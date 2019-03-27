package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.stage.Window;

/**
 * A simple camera that remains fixed in position as the scene changes
 * <p>
 * The StaticCamera's movement and zoom is relative to the centre of the
 * viewport. Its default position is the world's centre
 */
public class StaticCamera extends Camera {

	@Override
	public void update() {
		Bounds b = MainGame.getWorld().getLayoutBounds();
		Window w = MainGame.getRenderedScene().getWindow();
		Group world = MainGame.getWorld();
		world.setTranslateX((int) (positionX * scaleX - b.getWidth() / 2 + w.getWidth() / 2));
		world.setTranslateY((int) (positionY * scaleY - b.getHeight() / 2 + w.getHeight() / 2));
		world.setScaleX(scaleX);
		world.setScaleY(scaleY);
	}

	/**
	 * updates the world view
	 * 
	 * @param b     the Bounds of the screen
	 * @param w     the window rendering the camera
	 * @param world the world Group
	 */
	public void update(Bounds b, Window w, Group world) {
		world.setTranslateX((int) (positionX * scaleX - b.getWidth() / 2 + w.getWidth() / 2));
		world.setTranslateY((int) (positionY * scaleY - b.getHeight() / 2 + w.getHeight() / 2));
		world.setScaleX(scaleX);
		world.setScaleY(scaleY);
	}

}