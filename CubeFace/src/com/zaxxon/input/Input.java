package com.zaxxon.input;

import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Input {

	/**
	 * HashSet containing all keys pressed at any given time
	 */
	private static Set<KeyCode> keysPressed = new HashSet<KeyCode>();

	/**
	 * Adds handlers to a Stage for key presses and releases
	 * 
	 * @param attachedStage the Stage to attach handlers to
	 */
	public static void addHandlers(Stage attachedStage) {

		attachedStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.add(key.getCode());
				key.consume();
			}
		});
		attachedStage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.remove(key.getCode());
				key.consume();
			}
		});
	}

	/**
	 * gets the HashSet of all keys pressed
	 * 
	 * @return the HashSet of all keys pressed
	 */
	public static Set<KeyCode> getKeysPressed() {
		return keysPressed;
	}

	/**
	 * gets whether the HastSet contains the key or not
	 * 
	 * @param key the key being affected
	 * @return true if pressed else false
	 */
	public static boolean isKeyPressed(KeyCode key) {
		if (keysPressed.contains(key)) {
			return true;
		}
		return false;
	}

	/**
	 * removes a key from the HashSet - useful for single trigger events
	 * 
	 * @param key the key being affected
	 */
	public static void removeKeyPress(KeyCode key) {
		keysPressed.remove(key);
	}

}
