package com.zaxxon.client;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

import com.zaxxon.world.Sprite;
import com.zaxxon.world.mobile.Player;

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
	private BufferedImage[] sprites;
	private int zombieImage = 0;

	private Set<KeyCode> keysPressed = new HashSet<KeyCode>();

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
		BufferedImage bruteZombieSpriteSheet = null;
		try {
			File bruteZombieFile = new File("src/com/zaxxon/gameart/brute-zombie.png");
			bruteZombieSpriteSheet = ImageIO.read(bruteZombieFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		final int width = 128;
		final int height = 128;
		final int rows = 2;
		final int cols = 4;
		sprites = new BufferedImage[rows * cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[(i * cols) + j] = bruteZombieSpriteSheet.getSubimage(j * width, i * height, width, height);
			}
		}

		Image bruteZombieImg = SwingFXUtils.toFXImage(sprites[0], null);
		ImagePattern imgPat = new ImagePattern(bruteZombieImg);
		
		player = new Player(imgPat);
		foreground.getChildren().add(player);
		xPos = 300;
		

		root.setFocusTraversable(true);
		root.requestFocus();

		primaryStage.setScene(renderedScene);
		primaryStage.show();

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.add(key.getCode());
			}
		});

		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.remove(key.getCode());
			}
		});

		AnimationTimer animator = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				dealWithKeys();
				player.setX(xPos);
			}
		};
		animator.start();
	}

	private void dealWithKeys() {
		if (keysPressed.contains(KeyCode.LEFT)) {
			xPos -= 1;
		}
		if (keysPressed.contains(KeyCode.RIGHT)) {
			xPos += 1;
		}
		if (keysPressed.contains(KeyCode.SPACE)) {
			zombieImage++;
			Image bruteZombieImg = SwingFXUtils.toFXImage(sprites[zombieImage % sprites.length], null);
			ImagePattern imgPat = new ImagePattern(bruteZombieImg);
			player.setFill(imgPat);
			keysPressed.remove(KeyCode.SPACE);
		}
	}

}
