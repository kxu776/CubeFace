package com.zaxxon.world;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

public class Tile extends Sprite {

	private static ArrayList<Tile> allTiles = new ArrayList<Tile>();

	public Tile(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		this.setImage(SpriteImages.BACKGROUND_TILE_IMAGE);
		this.setRotate(ThreadLocalRandom.current().nextInt(0, 4) * 90);
		allTiles.add(this);
	}

	public static ArrayList<Tile> getAllTiles() {
		return allTiles;
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

	@Override
	public void delete() {
		allTiles.remove(this);
		MainGame.removeFromGame(this);
	}

}
