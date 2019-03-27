package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.stage.Window;

/**
 * A simple camera that can tracks a Sprite's position
 * <p>
 * The StaticCamera's movement and zoom is relative to the centre of the
 * viewport. Its default position is the Sprite's centre (calculated by its
 * Bounds)
 *
 */
public class TrackingCamera extends Camera {

	/**
	 * The Sprite that the TrackingCamer follows
	 */
	protected Sprite spriteToFollow;

	/**
	 * Creates a TrackingCamera that follows a specific Sprite
	 * 
	 * @param spriteToFollow The Sprite that is being tracked
	 */
	public TrackingCamera(Sprite spriteToFollow) {
		this.spriteToFollow = spriteToFollow;
		Bounds worldBounds = MainGame.getWorld().getLayoutBounds();
		positionX = worldBounds.getWidth() / 2;
		positionY = worldBounds.getHeight() / 2;
	}

	/**
	 * Changes the Sprite that the TrackingCamera follows
	 * 
	 * @param spriteToFollow The Sprite that is being tracked
	 */
	public void setSpriteToFollow(Sprite spriteToFollow) {
		this.spriteToFollow = spriteToFollow;
	}

	@Override
	public void update() {
		try {
			Bounds spriteBounds = spriteToFollow.getBoundsInParent();
			Bounds worldBounds = MainGame.getWorld().getLayoutBounds();
			Window displayWindow = MainGame.getRenderedScene().getWindow();
			Group world = MainGame.getWorld();
			double offsetX = (spriteBounds.getMaxX() + spriteBounds.getMinX()) / 2;
			double offsetY = (spriteBounds.getMaxY() + spriteBounds.getMinY()) / 2;
			world.setTranslateX(
					(int) ((positionX - offsetX) * scaleX - worldBounds.getWidth() / 2 + displayWindow.getWidth() / 2));
			world.setTranslateY((int) ((positionY - offsetY) * scaleY - worldBounds.getHeight() / 2
					+ displayWindow.getHeight() / 2));
			world.setScaleX(scaleX);
			world.setScaleY(scaleY);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
