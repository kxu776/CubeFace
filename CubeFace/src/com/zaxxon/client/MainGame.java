package com.zaxxon.client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import com.zaxxon.input.Input;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.ClientSender;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.Toolbox;
import com.zaxxon.world.TrackingCamera;
import com.zaxxon.ui.StatsBox;
import com.zaxxon.world.Camera;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;
import com.zaxxon.world.mobile.enemies.Hunter;
import com.zaxxon.world.mobile.enemies.Zombie;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainGame {

	private static Group grpGame;
	private static Group world;
	private static Group background;
	//private static Group foreground;
	private static Group foreground;
	private static Group overlay;
	private static Camera camera;
	private static LinkedList<Sprite> spriteList = new LinkedList<>();
	public static ArrayList<Player> playerList;
	public static ArrayList<Enemy> enemiesList;
	public static Client networkingClient;
	private static Scene renderedScene;
	private static double FPSreduction;
	public static ClientSender client;
	public static boolean multiplayer = false;
	private static Player player1;
	private static HashMap<String,Player> play = new HashMap<>();
	private static AnchorPane anchorPane;

	public static LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();

	public static void reset(Stage primaryStage) {
		// set up game group
		grpGame = new Group();
		grpGame.setId("grpGame");
		world = new Group();
		world.setId("world");
		background = new Group();
		background.setId("background");
		foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		grpGame.getChildren().add(world);
		grpGame.getChildren().add(overlay);
		world.getChildren().add(background);
		world.getChildren().add(foreground);

		//make a statsbox
		BorderPane borderPane = StatsBox.statsBox();

		//make a toolbox
		AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 3, "CubeFace");
		toolbox.setPrefWidth(998.0);
		toolbox.setId("toolbox");




		//make an anchor pane to hold the game and the stats box
		anchorPane = new AnchorPane();
		anchorPane.setTopAnchor(toolbox, 0.0);
		anchorPane.setLeftAnchor(toolbox, 0.0);
		anchorPane.setBottomAnchor(borderPane, 0.0);
		anchorPane.setRightAnchor(borderPane, 0.0);
		anchorPane.setLeftAnchor(grpGame,0.0);

		anchorPane.setCenterShape(true);
		anchorPane.getChildren().addAll(grpGame, borderPane, toolbox);
		anchorPane.setId("anchorpane");



		// set up new arrays and objects
		Wall.resetWalls();
		spriteList = new LinkedList<Sprite>();
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


		//make a rectangle
		Rectangle rect = new Rectangle(1000,500);
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		anchorPane.setClip(rect);



		// sets up the scene
		renderedScene = new Scene(anchorPane, 1000, 500);
		renderedScene.getStylesheets().add(MainMenu.class.getResource("maingame.css").toString());

		// loads the level
		Levels.generateLevel(Levels.LEVEL1, 256);
		
		// sets up the game camera
		camera = new TrackingCamera(player1);
	}

	public static void start(Stage primaryStage) {
		primaryStage.setScene(renderedScene);
		grpGame.setFocusTraversable(true);
		grpGame.requestFocus();
		primaryStage.setWidth(renderedScene.getWindow().getWidth());
		primaryStage.setHeight(renderedScene.getWindow().getHeight());

		Input.addHandlers(primaryStage);

		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				camera.update();
				for (Player player : playerList) {
					player.update(FPSreduction);
				}
				dealWithKeyInput();
				if (multiplayer) {
					sendNetworkUpdate();
					getUpdatesFromQueue();
				}
				updateEnemies();
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
		if (Input.isKeyPressed(KeyCode.W)) {
			camera.setPositionY(camera.getPositionY() + 1);
		}
		if (Input.isKeyPressed(KeyCode.S)) {
			camera.setPositionY(camera.getPositionY() - 1);
		}
		if (Input.isKeyPressed(KeyCode.A)) {
			camera.setPositionX(camera.getPositionX() + 1);
		}
		if (Input.isKeyPressed(KeyCode.D)) {
			camera.setPositionX(camera.getPositionX() - 1);
		}
		if (Input.isKeyPressed(KeyCode.Q)) {
			camera.setScaleX(camera.getScaleX() * 1.02);
			camera.setScaleY(camera.getScaleX());
		}
		if (Input.isKeyPressed(KeyCode.E)) {
			camera.setScaleX(camera.getScaleX() / 1.02);
			camera.setScaleY(camera.getScaleX());
		}
		if (Input.isKeyPressed(KeyCode.SPACE)) {
			System.out.println(renderedScene.getWindow().getWidth() + ", " + renderedScene.getWindow().getHeight());
		}
	}

	public static void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	public static void removeSprite(Sprite s) {
		for (Sprite searchingSprite : spriteList) {
			if (searchingSprite == s) {
				((Group) s.getParent()).getChildren().remove(s);
				spriteList.remove(searchingSprite);
				s = null;
				return;
			}
		}
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


	private static void sendNetworkUpdate() {
		client.setX(player1.getX());
		client.setY(player1.getY());
		client.setHealth(player1.getHealth());
		networkingClient.sendPlayerObj(client);
	}


	private static void getUpdatesFromQueue() {
		while (!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			if (!play.containsKey(data.getID())) {
				
				play.put(data.getID(), new Player());;	
				play.get(data.getID()).setX(500);
				play.get(data.getID()).setY(500);
				play.get(data.getID()).setId(data.getID());
				
				addSpriteToForeground(play.get(data.getID()));
			}
			for (Sprite s : spriteList) {
				if (data.getID().equals(s.getId())) {
					s.setX(data.getX());
					s.setY(data.getY());
					
					if (s instanceof MovableSprite) {
						((MovableSprite) s).setHealth(data.getHealth());
					}
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
