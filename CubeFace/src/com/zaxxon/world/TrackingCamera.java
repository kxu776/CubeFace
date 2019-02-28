package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.stage.Window;

public class TrackingCamera extends Camera {

	protected Sprite spriteToFollow;

	public TrackingCamera(Sprite s) {
		spriteToFollow = s;
	}

	@Override
	public void update() {
		Bounds spriteBounds = spriteToFollow.getBoundsInParent();
		Bounds worldBounds = MainGame.getWorld().getLayoutBounds();
		Window displayWindow = MainGame.getRenderedScene().getWindow();
		Group world = MainGame.getWorld();
		double offsetX = spriteBounds.getMaxX() - spriteBounds.getMinX();
		double offsetY = spriteBounds.getMaxY() - spriteBounds.getMinY();
		world.setTranslateX((int) (positionX * scaleX - worldBounds.getWidth() / 2 + displayWindow.getWidth() / 2) + offsetX);
		world.setTranslateY((int) (positionY * scaleY - worldBounds.getHeight() / 2 + displayWindow.getHeight() / 2) + offsetY);
		world.setScaleX(scaleX);
		world.setScaleY(scaleY);
	}

}
