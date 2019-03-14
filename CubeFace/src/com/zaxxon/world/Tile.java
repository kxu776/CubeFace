package com.zaxxon.world;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

public class Tile extends Sprite {

	public Tile(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.setImage(SpriteImages.BACKGROUND_TILE_IMAGE);
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}

}
