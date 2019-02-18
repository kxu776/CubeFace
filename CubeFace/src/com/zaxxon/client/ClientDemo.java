package com.zaxxon.client;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

import com.zaxxon.input.Input;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.mobile.Player;

import com.zaxxon.world.mobile.enemies.Enemy;
import javafx.animation.*;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class ClientDemo extends Application {

	private int xPos = 0;
	private Player player;
	private static Input input;
	private int zombieImage = 0;

	public Set<KeyCode> keysPressed = new HashSet<KeyCode>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Demo");

		Group root = new Group();
		Group background = new Group();
		Group foreground = new Group();
		root.getChildren().add(background);
		root.getChildren().add(foreground);

		Scene renderedScene = new Scene(root, 800, 600);
		
		
		player = new Player();
		foreground.getChildren().add(player);
		Enemy enemy = new Enemy(1,1);
		foreground.getChildren().add(enemy);

		root.setFocusTraversable(true);
		root.requestFocus();

		primaryStage.setScene(renderedScene);
		primaryStage.show();
		
		Input.addHandlers(primaryStage);

		AnimationTimer animator = new AnimationTimer() {
			
			public void handle(long currentNanoTime) {
				
				player.update(1);
				enemy.update(1, player);
			}
		};
		animator.start();
	}

}
