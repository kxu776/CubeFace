package zaxxon.learningEnvironment.javafx;

import java.util.*;

import javafx.animation.*;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class GettingStarted extends Application {

	private int xPos = 0;

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

		Rectangle puppyRect = new Rectangle(100, 100);
		Image puppyImg = new Image("/zaxxon/learningEnvironment/javafx/puppy.png");
		ImagePattern imgPat = new ImagePattern(puppyImg);
		puppyRect.setFill(imgPat);

		foreground.getChildren().add(puppyRect);

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
				puppyRect.setX(xPos + currentNanoTime/10000000000l-1000);
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
	}

}
