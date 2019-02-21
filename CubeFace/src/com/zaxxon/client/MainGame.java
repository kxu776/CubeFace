package com.zaxxon.client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import com.zaxxon.input.Input;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.ClientSender;
import com.zaxxon.world.Camera;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainGame {

	private static Group grpGame;
	private static Group world;
	private static Group background;
	private static Group foreground;
	private static Group overlay;
	private static Camera camera;
	private static LinkedList<Sprite> spriteList = new LinkedList<>();
	private static ArrayList<Player> playerList;
	private static Client networkingClient;
	private static Scene renderedScene;
	private static double FPSreduction;
	
	public static LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();

	public static void reset() {
		// set up groups
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

		// set up new arrays and objects
		Wall.resetWalls();
		spriteList = new LinkedList<Sprite>();
		playerList = new ArrayList<Player>();
		camera = new Camera();
		
		Player player1 = new Player();
		player1.setX(500);
		player1.setY(500);
		addSpriteToForeground(player1);

		Enemy enemy = new Enemy(600,600);
		addSpriteToForeground(enemy);

		// sets the scene to the screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		FPSreduction = 60.0 / gd.getDisplayMode().getRefreshRate();

		// sets up the scene
		renderedScene = new Scene(grpGame, width, height);

		// loads the level
		Levels.generateLevel(Levels.LEVEL1, 256);
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
				transformWorld();
				for (Player player : playerList) {
					player.update(FPSreduction);
				}
				dealWithKeyInput();
				//sendNetworkUpdate();
				updateEnemies();
			}
		};
		mainGameLoop.start();
	}

	public static Scene getRenderedScene() {
		return renderedScene;
	}

	private static void transformWorld() {
		world.setTranslateX((int) (camera.getPositionX() * camera.getScaleX() - world.getLayoutBounds().getWidth() / 2
				+ renderedScene.getWindow().getWidth() / 2));
		world.setTranslateY((int) (camera.getPositionY() * camera.getScaleY() - world.getLayoutBounds().getHeight() / 2
				+ renderedScene.getWindow().getHeight() / 2));
		world.setScaleX(camera.getScaleX());
		world.setScaleY(camera.getScaleY());
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
		for(Sprite searchingSprite : spriteList) {
			if(searchingSprite == s) {
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

	/*private void sendNetworkUpdate() {
		networkingClient.spritesToString(spriteList); // Compiles ArrayList<string> of concatenated sprite attributes.
		// actually send the packets here
	}*/
	
	private static void getUpdatesFromQueue() {
		while(!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			for(Sprite s : spriteList) {
				if(data.getID() == Integer.parseInt(s.getId())) {
					s.setX(data.getX());
					s.setY(data.getY());
					if(s instanceof MovableSprite) {
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
			if (sprite.getClass() == Enemy.class) { // Typechecks for enemies
				if (!sprite.isAlive()) {
					spriteList.remove(sprite);
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
