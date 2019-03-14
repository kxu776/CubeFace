package com.zaxxon.client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.ClientSender;

import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.Toolbox;

import com.zaxxon.world.TrackingCamera;
import com.zaxxon.ui.StatsBox;
import com.zaxxon.world.Camera;
import com.zaxxon.world.CollidableRectangle;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;
import com.zaxxon.world.mobile.MultiplayerPlayer;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;
import com.zaxxon.world.mobile.enemies.Hunter;
import com.zaxxon.world.mobile.enemies.Zombie;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainGame {

	private static Group grpGame;
	private static Group world;
	private static Group background;
	private static Group foreground;
	private static Group overlay;
	private static Group collidables;
	private static Camera camera;
	private static ConcurrentLinkedQueue<Sprite> spriteList;
	public static ArrayList<Player> playerList;
	public static ArrayList<Enemy> enemiesList;
	public static Client networkingClient;
	private static Scene renderedScene;
	private static double FPSreduction;
	public static ClientSender client;
	public static boolean multiplayer = false;
	private static boolean spawn = false;
	static boolean f = false;

	private static Player player1;
	private static HashMap<String, MultiplayerPlayer> play = new HashMap<>();
	private static AnchorPane anchorPane;

	public static LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();

	public static void reset(Stage primaryStage) {
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
		grpGame.getChildren().add(world);
		grpGame.getChildren().add(overlay);
		grpGame.prefWidth(998);
		grpGame.prefHeight(498);

		// make a rectangle
		Rectangle gameRect = new Rectangle(998, 498);
		gameRect.setLayoutX(1);
		gameRect.setLayoutY(1);

		// clip the group
		grpGame.setClip(gameRect);

		world.getChildren().add(background);
		world.getChildren().add(foreground);
		world.getChildren().add(collidables);

		// make a statsbox
		BorderPane borderPane = StatsBox.statsBox();

		// make a toolbox
		AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 3, "CubeFace");
		toolbox.setPrefWidth(998.0);
		toolbox.setId("toolbox");

		// make an anchor pane to hold the game and the stats box
		anchorPane = new AnchorPane();
		anchorPane.setTopAnchor(toolbox, 0.0);
		anchorPane.setLeftAnchor(toolbox, 0.0);
		anchorPane.setBottomAnchor(borderPane, 0.0);
		anchorPane.setRightAnchor(borderPane, 0.0);
		anchorPane.setCenterShape(true);
		anchorPane.getChildren().addAll(grpGame, borderPane, toolbox);
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

		Zombie enemy = new Zombie(600, 600);
		Hunter enemy2 = new Hunter(1800, 1700);
		addSpriteToForeground(enemy);
		addSpriteToForeground(enemy2);

		// sets the scene to the screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		FPSreduction = 60.0 / 60;

		// make a rectangle
		Rectangle rect = new Rectangle(1000, 500);
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		anchorPane.setClip(rect);

		// sets up the scene
		renderedScene = new Scene(anchorPane, 1000, 500);
		renderedScene.getStylesheets().add(MainMenu.class.getResource("maingame.css").toString());

		// loads the level
		Levels.generateLevel(Levels.LEVEL2);
		// sets up the game camera
		camera = new TrackingCamera(player1);
	}

	public static void start(Stage primaryStage) {
		primaryStage.setScene(renderedScene);
		grpGame.setFocusTraversable(true);
		grpGame.requestFocus();
		primaryStage.setWidth(renderedScene.getWindow().getWidth());
		primaryStage.setHeight(renderedScene.getWindow().getHeight());
		anchorPane.setPrefWidth(renderedScene.getWindow().getWidth());
		anchorPane.setPrefHeight(renderedScene.getWindow().getHeight());

		Input.addHandlers(primaryStage);

		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				dealWithKeyInput();
				for (Player player : playerList) {
					player.update(FPSreduction);
				}
				if (multiplayer) {
					sendNetworkUpdate();
					getUpdatesFromQueue();
					for (HashMap.Entry<String, MultiplayerPlayer> c : play.entrySet()) {
						c.getValue().update(FPSreduction);
					}
				}
				updateEnemies();
				camera.update();
			}
		};
		mainGameLoop.start();
	}

	public static Scene getRenderedScene() {
		return renderedScene;
	}

	public static Group getWorld() {
		return world;
	}

	private static void dealWithKeyInput() {
	}

	public static void removeFromGame(Object o) {
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

	private static void removeFromGroup(Group g, Object o) {
		Platform.runLater(() -> {
			g.getChildren().remove(o);
		});
	}

	public static void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	public static void addSpriteToForeground(Sprite s) {
		foreground.getChildren().add(s);
		spriteList.add(s);
		if (s.getClass() == Player.class) {
			playerList.add((Player) s);
		}
	}

	public static void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}

	public static void addCollidable(CollidableRectangle c) {
		collidables.getChildren().add(c);
	}

	private static void sendNetworkUpdate() {
		if (spawn == false) {
			System.out.println("Spawning in...");
			client.setX(player1.getX());
			client.setY(player1.getY());
			client.setHealth(player1.getHealth());
			networkingClient.sendPlayerObj(client);
			spawn = true;
		}

		if (player1.getdir() == (FacingDir.up)) {
			client.pos = 1;
		} else if (player1.getdir() == (FacingDir.down)) {
			client.pos = 2;
		} else if (player1.getdir() == (FacingDir.left)) {
			client.pos = 3;
		} else if (player1.getdir() == (FacingDir.right)) {
			client.pos = 4;
		}

		// Standing still and shooting
		if (Input.isKeyPressed(KeyCode.SPACE) && ((player1.getX() - client.getX()) == 0.0)
			&& ((player1.getY() - client.getY()) == 0.0)) {
			if(f == false) {
				client.setX(player1.getX());
				client.setY(player1.getY());
				client.setHealth(player1.getHealth());
				client.shoot = true;
				networkingClient.sendPlayerObj(client);
				client.shoot = false;
				f = true;
			}
		}
		// Standing still
		if (((player1.getX() - client.getX()) == 0.0) && ((player1.getY() - client.getY()) == 0.0)) {
			return;
		}

		if (Input.isKeyPressed(KeyCode.SPACE)) {
			if(!f) {
				f = true;
				client.shoot = true;
			}
		}
		// Moving
		client.setX(player1.getX());
		client.setY(player1.getY());
		client.setHealth(player1.getHealth());
		networkingClient.sendPlayerObj(client);
		client.shoot = false;
		f = false;
	}

	private static void getUpdatesFromQueue() {

		while (!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			String id = data.getID().trim();
			if (!play.containsKey(id)) {
				play.put(id, new MultiplayerPlayer());
				play.get(id).setX(900);
				play.get(id).setY(900);
				play.get(id).setId(id);
				play.get(id).weapon.getCurrentWeapon().test =true;
				System.out.println("Creating player on this ID " + id);
				addSpriteToForeground(play.get(id));
			}
		
			
			Iterator<Sprite> iterator= spriteList.iterator();
			while ( iterator.hasNext()) {
			    Sprite sprite = iterator.next();
			    if (sprite.getId().equals(id)) {
			    		sprite.setX(data.getX());
					sprite.setY(data.getY());
					((MultiplayerPlayer) sprite).setDir(data.pos);	
			    }
				
				if (sprite instanceof MovableSprite) {
					((MovableSprite) sprite).setHealth(data.getHealth());
				}
				
				if ((data.shoot == true)) {
					FacingDir m = play.get(id).getdir();
					Vector2 vect = play.get(id).weapon.getFacingDirAsVector(m);
					Vector2 pos = play.get(id).weapon.playerPos;
					
					play.get(id).weapon.getCurrentWeapon().fire(vect,pos, true);
				}
			}
		}
	}

	private static void updateEnemies() {
		// Iterates through enemies, updates pos relative to player
		boolean updatedPlayerPos = false;
		for (Sprite sprite : spriteList) {
			if (sprite instanceof Enemy) { // Typechecks for enemies
				if (!sprite.isAlive()) {
					spriteList.remove(sprite);
					enemiesList.remove(sprite);
				} else {
					Pair<Double, Player> closestPlayer = null;
					for (Player player : playerList) {
						if (closestPlayer == null) {
							closestPlayer = new Pair<Double, Player>(sprite.getDistanceToSprite(player), player);
						} else if (sprite.getDistanceToSprite(player) < closestPlayer.getKey()) {
							closestPlayer = new Pair<Double, Player>(sprite.getDistanceToSprite(player), player);
						}
					}
					((Enemy) sprite).update(FPSreduction, closestPlayer.getValue());
				}
			}
		}
	}

}
