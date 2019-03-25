
package com.zaxxon.networking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.zaxxon.client.MainGame;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.ClientSender;
import com.zaxxon.sound.MusicPlayer;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.StatsBox;
import com.zaxxon.ui.tools.Toolbox;
import com.zaxxon.world.Camera;
import com.zaxxon.world.CollidableRectangle;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Tile;
import com.zaxxon.world.TrackingCamera;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;
import com.zaxxon.world.mobile.enemies.Hunter;
import com.zaxxon.world.mobile.enemies.Zombie;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The main window for the game and all its processes
 * 
 * @author Philip Eagles
 *
 */
/**
 * @author Philip Eagles
 *
 */
public class ServerGame {

	private static Group grpGame;
	private static Group world;
	private static Group background;
	private static Group foreground;
	private static Group overlay;
	private static Group collidables;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	private static Camera camera;
	private static ConcurrentLinkedQueue<Sprite> spriteList;
	public static ArrayList<Player> playerList;
	public static ArrayList<Enemy> enemiesList;
	public static Client networkingClient;
	private static Scene renderedScene;
	public static ClientSender client;
	public static boolean multiplayer = false;
	private static boolean spawn = false;
	static boolean fired = false;


	private static Player player1;
	public ConcurrentHashMap<String, Player> play = new ConcurrentHashMap<>();
	private static AnchorPane anchorPane;

	private static long fpsLong;
	private static double normalisedFPS;
	private static long gameStartTime;

	public LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();
	public LinkedBlockingQueue<String> enemyUpdateQueue = new LinkedBlockingQueue<String>();


	/**
	 * sets the focus on the game scene for control input
	 */
	public  void setGameFocus() {
		grpGame.requestFocus();
	}

	private  void calculateFPS() {
		double smoothingFactor = 0.05;
		normalisedFPS = 1.0 / ((1000.0 / (System.currentTimeMillis() - fpsLong) / 60) * smoothingFactor
				+ ((1.0 / normalisedFPS) * (1.0 - smoothingFactor)));
		fpsLong = System.currentTimeMillis();
	}

	public  long getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Getter for the renderedScene Scene
	 * 
	 * @return the renderedScene
	 */
	public  Scene getRenderedScene() {
		return renderedScene;
	}

	/**
	 * Getter for the world Group
	 * 
	 * @return the world
	 */
	public  Group getWorld() {
		return world;
	}

	private  void dealWithKeyInput() {
	}

	public  void removeFromGame(Object o) {
		if (o instanceof Rectangle) {
			Sprite s = (Sprite) o;
			removeFromGroup((Group) s.getParent(), o);
			if (spriteList.contains(s)) {
				spriteList.remove(s);
				if (o instanceof Player) {
					Player p = (Player) o;
					playerList.remove(p);
				} else if (o instanceof Enemy) {
					Enemy e = (Enemy) o;
					enemiesList.remove(e);
				}
			}
		} else if (o instanceof CollidableRectangle) {
			CollidableRectangle cr = (CollidableRectangle) o;
			removeFromGroup((Group) cr.getParent(), o);
		}
	}

	private  void removeFromGroup(Group g, Object o) {
		Platform.runLater(() -> {
			g.getChildren().remove(o);
		});
	}

	/**
	 * adds a Sprite to the background of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a Sprite to the foreground of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToForeground(Sprite s) {
		Platform.runLater(() -> {
			foreground.getChildren().add(s);
			spriteList.add(s);
			if (s.getClass() == Player.class) {
				playerList.add((Player) s);
			}
		});
	}

	/**
	 * adds a Sprite to the overlay of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a CollidableRectangle to the collidable of the game
	 * 
	 * @param c the CollidableRectangle to be added
	 */
	public  void addCollidable(CollidableRectangle c) {
		collidables.getChildren().add(c);
	}

	

	private  void getPlayerUpdatesFromQueue() {
		while (!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			String id = data.getID().trim();
			newPlayer(data);

			Iterator<Sprite> iterator = spriteList.iterator();
			while (iterator.hasNext()) {
				Sprite sprite = iterator.next();
				if (sprite.getId().equals(id)) {
					sprite.setX(data.getX());
					sprite.setY(data.getY());
					((Player) sprite).setDir(data.pos);
					if (sprite instanceof MovableSprite) {
						((MovableSprite) sprite).setHealth(data.getHealth());
					}
					if ((play.get(id).weaponManager.getCurrentWeaponNum() != data.currWep)) {
						play.get(id).weaponManager.setCurrentWeapon(data.getCurrWep());
						play.get(id).weaponManager.getCurrentWeapon().test = true;
					}
					if ((data.shoot == true)) {
						FacingDir m = play.get(id).getdir();
						Vector2 vect = play.get(id).weaponManager.getFacingDirAsVector(m);
						Vector2 pos = play.get(id).weaponManager.playerPos;
						Vector2 wepPos = play.get(id).weaponManager.getWeaponPos(pos, play.get(id).getplayerDimensions(), vect);
						play.get(id).weaponManager.getCurrentWeapon().fire(vect, wepPos, true);
					}
				}
			}
		}
	}


	public  void removeAllMp() {
		for (ConcurrentHashMap.Entry<String, Player> players : play.entrySet()) {
			getSprite(players.getKey()).delete();
			removeFromGame(getSprite(players.getKey()));
		}
		play.clear();
	}

	/**
	 * Getter for the spriteList
	 * 
	 * @return the spriteList
	 */
	public  ConcurrentLinkedQueue<Sprite> getSpriteList() {
		return spriteList;
	}

	public  void newPlayer(ClientSender data) {
		String id = data.getID();
		if(!play.containsKey(data.getID())) {
				play.put(id, new Player());
				Player player = play.get(id);
				player.setX(data.getX());
				player.setY(data.getY());
				player.setId(id);
				player.mp = true;
				player.weaponManager.getCurrentWeapon().test = true;
				player.weaponManager.mp = true;
				addSpriteToForeground(player);
		}
	}
	
	/**
	 * returns a Sprite based off its unique ID
	 * 
	 * @param id the ID of the Sprite
	 * @return the Sprite if it exists else null
	 */
	public  Sprite getSprite(String id) {
		for (Sprite sprite : spriteList) {
			if (sprite.getId().equals(id)) {
				return sprite;
			}
		}
		return null;
	}
	public  void addZombie() {
		Zombie zombie = new Zombie(500,500);
		addSpriteToForeground(zombie);
	}
	
	public  void startMP() {
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		// enemiesList = new ArrayList<Enemy>();
		fpsLong = System.currentTimeMillis();
		normalisedFPS = 1;
		gameStartTime = System.currentTimeMillis();


		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				for (Player player : playerList) {
					player.update(normalisedFPS);
				}
				getPlayerUpdatesFromQueue();
				// updateEnemies();
				calculateFPS();
			}
		};
		mainGameLoop.start();
	}

	public void reset() {
		// set up game groups
		grpGame = new Group();
		grpGame.setId("grpGame");
		world = new Group();
		world.setId("world");
		collidables = new Group();
		collidables.setId("collidables");
		background = new Group();
		background.setId("background");
		foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		world.getChildren().add(background);
		world.getChildren().add(foreground);
		world.getChildren().add(collidables);
		grpGame.getChildren().add(world);
		grpGame.getChildren().add(overlay);

		
		grpGame.prefWidth(998);
		grpGame.prefHeight(498);

		Wall.resetWalls();
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		// enemiesList = new ArrayList<Enemy>();

		
		// loads the level
		Levels.generateLevel(Levels.LEVEL2);
	}

	
	public void reset(Stage primaryStage, MusicPlayer music) {
		// set up game groups
		grpGame = new Group();
		grpGame.setId("grpGame");
		world = new Group();
		world.setId("world");
		collidables = new Group();
		collidables.setId("collidables");
		background = new Group();
		background.setId("background");
		foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		world.getChildren().add(background);
		world.getChildren().add(foreground);
		world.getChildren().add(collidables);
		grpGame.getChildren().add(world);
		grpGame.getChildren().add(overlay);
		double[] xOffset = { 0 }; // array for making window movable
		double[] yOffset = { 0 };

		// make a rectangle
		Rectangle gameRect = new Rectangle(WIDTH-2, HEIGHT-2);
		gameRect.setLayoutX(1);
		gameRect.setLayoutY(1);

		grpGame.prefWidth(WIDTH);
		grpGame.prefHeight(HEIGHT);
		// clip the group
		grpGame.setClip(gameRect);

		// make a statsbox
		BorderPane borderPane = StatsBox.statsBox();

		// make a toolbox
		AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 3, "CubeFace");
		toolbox.setPrefWidth(WIDTH-2);
		toolbox.setId("toolbox");

		// make an audio button
		Button audio = new Button();
		// load audio icon
		// load the icon
		Image audioIcon = new Image(MainMenu.class.getResource("img/audio.png").toString());
		ImageView audioView = new ImageView(audioIcon); // make an imageview for the minimise icon
		audio.setGraphic(audioView); // add the image to the button
		audio.setOnAction(e -> {
			music.stop();
			MainGame.setGameFocus();
		});
		audio.setStyle("-fx-background-color: none; -fx-border: none; -fx-padding: 25 0 0 5;");

		// make an anchor pane to hold the game and the stats box
		anchorPane = new AnchorPane();
		anchorPane.setTopAnchor(toolbox, 0.0);
		anchorPane.setLeftAnchor(toolbox, 0.0);
		anchorPane.setBottomAnchor(borderPane, 0.0);
		anchorPane.setRightAnchor(borderPane, 0.0);
		anchorPane.setLeftAnchor(audio, 0.0);
		anchorPane.setCenterShape(true);
		anchorPane.getChildren().addAll(grpGame, borderPane, toolbox, audio);
		anchorPane.setId("anchorpane");

		// set up new arrays and objects
		Wall.resetWalls();
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		enemiesList = new ArrayList<Enemy>();
		player1 = new Player();
		player1.setX(500);
		player1.setY(500);
		addSpriteToForeground(player1);

		client = new ClientSender(player1.getX(), player1.getY(), player1.getHealth());

		

		// make a rectangle
		Rectangle rect = new Rectangle(WIDTH, HEIGHT);
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		anchorPane.setClip(rect);

		// sets up the scene
		renderedScene = new Scene(anchorPane, WIDTH, HEIGHT);
		renderedScene.setFill(Color.TRANSPARENT);
		renderedScene.getStylesheets().add(MainMenu.class.getResource("css/maingame.css").toString());

		// make it movable
		renderedScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset[0] = event.getSceneX();
				yOffset[0] = event.getSceneY();
			}
		});

		renderedScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset[0]);
				primaryStage.setY(event.getScreenY() - yOffset[0]);
			}
		});

		// loads the level
		Levels.generateLevel(Levels.LEVEL2);
		// sets up the game camera
		camera = new TrackingCamera(player1);
	}


}
