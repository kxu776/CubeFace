package com.zaxxon.world;

import javafx.scene.paint.Color;
public class Tile extends Sprite {
	
	public Tile(double x, double y, Color colour) {
		setX(x - 5);
		setY(y - 5);
		setWidth(10);
		setHeight(10);
		setFill(colour);
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

}
