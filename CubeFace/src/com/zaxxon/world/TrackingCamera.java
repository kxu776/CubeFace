package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.stage.Window;

public class TrackingCamera extends Camera {

	protected Sprite spriteToFollow;

	public TrackingCamera(Sprite s) {
		spriteToFollow = s;
		Bounds worldBounds = MainGame.getWorld().getLayoutBounds();
		positionX = worldBounds.getWidth() / 2;
		positionY = worldBounds.getHeight() / 2;
	}

	@Override
	public void update() {
		Bounds spriteBounds = spriteToFollow.getBoundsInParent();
		Bounds worldBounds = MainGame.getWorld().getLayoutBounds();
		Window displayWindow = MainGame.getRenderedScene().getWindow();
		Group world = MainGame.getWorld();
		double offsetX = (spriteBounds.getMaxX() + spriteBounds.getMinX()) / 2;
		double offsetY = (spriteBounds.getMaxY() + spriteBounds.getMinY()) / 2;
		world.setTranslateX((int) ((positionX - offsetX) * scaleX - worldBounds.getWidth() / 2 + displayWindow.getWidth() / 2));
		world.setTranslateY((int) ((positionY - offsetY) * scaleY - worldBounds.getHeight() / 2 + displayWindow.getHeight() / 2));
		world.setScaleX(scaleX);
		world.setScaleY(scaleY);
	}

}
