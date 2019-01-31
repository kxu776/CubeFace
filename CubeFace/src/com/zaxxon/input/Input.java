package com.zaxxon.input;

import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Input {

	private Set<KeyCode> keysPressed = new HashSet<KeyCode>();

	public Input(Stage attachedStage) {
		attachedStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.add(key.getCode());
			}
		});
		attachedStage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				keysPressed.remove(key.getCode());
			}
		});
	}

	public Set<KeyCode> getKeysPressed() {
		return keysPressed;
	}

	public boolean isKeyPressed(KeyCode key) {
		if (keysPressed.contains(key)) {
			return true;
		}
		return false;
	}

}
