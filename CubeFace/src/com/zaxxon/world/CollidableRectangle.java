package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.scene.shape.Rectangle;

public class CollidableRectangle extends Rectangle {

	public CollidableRectangle(double width, double height, double x, double y) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		setVisible(false);
	}
	
	public void delete() {
		MainGame.removeFromGame(this);
	}

}
