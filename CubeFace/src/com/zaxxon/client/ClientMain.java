package com.zaxxon.client;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.zaxxon.input.Input;
import com.zaxxon.world.Sprite;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application implements Runnable {

	private int width = 1280;
	private int height = 720;
	
	private Thread thread;
	private boolean running = false;
	private Input inputDetector;
	private Group root;
	private Group world;
	private Group overlay;
	private LinkedList<Sprite> spriteList = new LinkedList<>();
	
	public static void main(String[] args) {
		new ClientMain().start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Client");
		thread.start();
	}

	@Override
	public void run() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Cubeface");

		root = new Group();
		root.setId("root");
		world = new Group();
		world.setId("world");
		Group background = new Group();
		background.setId("background");
		Group foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		root.getChildren().add(world);
		root.getChildren().add(overlay);
		world.getChildren().add(background);
		world.getChildren().add(foreground);

		Scene renderedScene = new Scene(root, width, height);
		primaryStage.setScene(renderedScene);
		primaryStage.show();
		
		root.setFocusTraversable(true);
		root.requestFocus();
		
		inputDetector = new Input(primaryStage);
		
		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {

			}
		};
		mainGameLoop.start();
	}
	
	public void addSpriteToBackground(Sprite s) {
		((Group) world.lookup("background")).getChildren().add(s);
		spriteList.add(s);
	}
	
	public void addSpriteToForeground(Sprite s) {
		((Group) world.lookup("foreground")).getChildren().add(s);
		spriteList.add(s);
	}
	
	public void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}
}

