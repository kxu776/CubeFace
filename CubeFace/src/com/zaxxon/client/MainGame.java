package com.zaxxon.client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.LinkedList;

import com.zaxxon.input.Input;
import com.zaxxon.networking.Client;
import com.zaxxon.world.Camera;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainGame {

	private Group grpGame;
	private Group world;
	private Group background;
	private Group foreground;
	private Group overlay;
	private Camera camera;
	private LinkedList<Sprite> spriteList = new LinkedList<>();
	private Client networkingClient;
	private ArrayList<Player> playerList;
	private Scene renderedScene;

	public MainGame() {
		reset();
	}

	public void reset() {
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
		this.addSpriteToForeground(player1);

		// sets the scene to the screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();

		// sets up the scene
		renderedScene = new Scene(grpGame, width, height);

		// loads the level
		SampleLevel.generateLevel(this);
	}

	public void start(Stage primaryStage) {
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
					player.update(1);
				}
				dealWithKeyInput();
				//sendNetworkUpdate();
				updateEnemies();
			}
		};
		mainGameLoop.start();
	}

	public Scene getRenderedScene() {
		return renderedScene;
	}

	private void transformWorld() {
		world.setTranslateX(camera.getPositionX() * camera.getScaleX() - world.getLayoutBounds().getWidth() / 2
				+ renderedScene.getWindow().getWidth() / 2);
		world.setTranslateY(camera.getPositionY() * camera.getScaleY() - world.getLayoutBounds().getHeight() / 2
				+ renderedScene.getWindow().getHeight() / 2);
		world.setScaleX(camera.getScaleX());
		world.setScaleY(camera.getScaleY());
	}

	private void dealWithKeyInput() {
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

	public void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	public void addSpriteToForeground(Sprite s) {
		foreground.getChildren().add(s);
		spriteList.add(s);
		if (s.getClass() == Player.class) {
			playerList.add((Player) s);
		}
	}

	public void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}

	/*private void sendNetworkUpdate() {
		networkingClient.spritesToString(spriteList); // Compiles ArrayList<string> of concatenated sprite attributes.
		// actually send the packets here
	}*/

	private void updateEnemies() {
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
					((Enemy) sprite).update(1, closestPlayer.getValue());
				}
			}
		}
	}

}
