package com.zaxxon.client;

import java.util.LinkedList;

import com.zaxxon.input.Input;
import com.zaxxon.networking.Client;
import com.zaxxon.world.Camera;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.networking.ClientSender;

import com.zaxxon.world.mobile.enemies.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


//public class ClientMain extends Application implements Runnable {
public class ClientMain extends Application {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

//	private Thread thread;
//	private boolean running = false;
	private Group root;
	private Group world;
	private Group background;
	private Group foreground;
	private Group overlay;
	private Camera camera;
	private LinkedList<Sprite> spriteList = new LinkedList<>();
	ClientSender c;
	private Client networkingClient;
	private Player player;
	

	public static void main(String[] args) {
//		new ClientMain().start();
		launch();
	}

	private void initialise() {
		camera = new Camera();
	}

//	public void start() {
//		running = true;
//		thread = new Thread(this, "Client");
//		thread.start();
//	}
//
//	@Override
//	public void run() {
//		launch();
//	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialise();

		primaryStage.setTitle("Cubeface");

		root = new Group();
		root.setId("root");
		world = new Group();
		world.setId("world");
		background = new Group();
		background.setId("background");
		foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		root.getChildren().add(world);
		root.getChildren().add(overlay);
		world.getChildren().add(background);
		world.getChildren().add(foreground);

		player = new Player();
		foreground.getChildren().add(player);
	

		//---NETWORK INTEGRATION
		
		// Name would need to be unique 
		networkingClient = new Client("localhost", 4444,"Name");
		networkingClient.start();
		c  = new ClientSender(player.getX(), player.getY(), player.getHealth());
		

		Scene renderedScene = new Scene(root, WIDTH, HEIGHT);
		primaryStage.setScene(renderedScene);
		primaryStage.show();

		root.setFocusTraversable(true);
		root.requestFocus();

		Input.addHandlers(primaryStage);

		Sprite[] bg = SampleLevel.generateBackground();
		for (int i = 0; i < bg.length; i++) {
			Sprite s = bg[i];
			if (s != null) {
				s.setX(SampleLevel.SIZE * (i % SampleLevel.STATE_SHEET.length));
				s.setY(SampleLevel.SIZE * (i / SampleLevel.STATE_SHEET.length));
				addSpriteToBackground(s);
			}
		}

		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				transformWorld();
				player.update(1);
				dealWithKeyInput();
				sendNetworkUpdate();
				updateEnemies(player.getX(), player.getY());
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}

			}
		};
		mainGameLoop.start();
	}
	
	private void transformWorld() {
		world.setTranslateX(camera.getPositionX() * camera.getScaleX() - world.getLayoutBounds().getWidth() / 2
				+ WIDTH / 2);
		world.setTranslateY(camera.getPositionY() * camera.getScaleY() - world.getLayoutBounds().getHeight() / 2
				+ HEIGHT / 2);
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
			System.out.println("(" + world.getTranslateX() + ", " + world.getTranslateY() + "), [" + world.getScaleX()
					+ ", " + world.getScaleY() + "]");
		}
	}

	public void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	public void addSpriteToForeground(Sprite s) {
		foreground.getChildren().add(s);
		spriteList.add(s);
	}

	public void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}


	public void sendNetworkUpdate(){
		c.setX(player.getX());c.setY(player.getY());c.setHealth(player.getHealth());
		networkingClient.sendPlayerObj(c);
		//networkingClient.spritesToString(spriteList);	//Compiles ArrayList<string> of concatenated sprite attributes.
		//actually send the packets here
	}


	public void updateEnemies(double pX, double pY){
		//Iterates through enemies, updates pos relative to player
		for(Sprite sprite: spriteList){
			if(sprite.getClass()== Enemy.class){  //Typechecks for enemies
				sprite.update();
			}
		}
	}

}
