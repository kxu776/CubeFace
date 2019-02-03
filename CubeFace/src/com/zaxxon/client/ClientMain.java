package com.zaxxon.client;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
=======

import java.util.LinkedList;

>>>>>>> aa814f30437ff0aae8982f10bcbc498b79ed5e43
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
	private Group root;
	private Group world;
	private Group background;
	private Group foreground;
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

		Scene renderedScene = new Scene(root, width, height);
		primaryStage.setScene(renderedScene);
		primaryStage.show();
		
		root.setFocusTraversable(true);
		root.requestFocus();
		
		Input.addHandlers(primaryStage);
		
		Sprite[] bg = SampleLevel.generateBackground();
		for(int i = 0; i < bg.length; i++) {
			Sprite s = bg[i];
			if (s != null) {
				s.setX(SampleLevel.SIZE * (i % SampleLevel.STATE_SHEET.length));
				s.setY(SampleLevel.SIZE * (i / SampleLevel.STATE_SHEET.length));
				addSpriteToBackground(s);
			}
		}
		
		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {

			}
		};
		mainGameLoop.start();
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
}

