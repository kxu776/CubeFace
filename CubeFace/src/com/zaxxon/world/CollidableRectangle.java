package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.scene.shape.Rectangle;

/**
 * an abstract, invisible Rectangle used for hitbox collisions
 */
public class CollidableRectangle extends Rectangle {

	/**
	 * constructor for a new CollidableRectangle
	 * 
	 * @param width  the width of the CollidableRectangle in pixels
	 * @param height the height of the CollidableRectangle in pixels
	 * @param x      the x position of the CollidableRectangle in pixels
	 * @param y      the y position of the CollidableRectangle in pixels
	 */
	public CollidableRectangle(double width, double height, double x, double y) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		setVisible(false);
	}

	/**
	 * destructor
	 */
	public void delete() {
		MainGame.removeFromGame(this);
	}

}
