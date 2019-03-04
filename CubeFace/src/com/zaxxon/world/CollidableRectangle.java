package com.zaxxon.world;

import javafx.scene.shape.Rectangle;

public class CollidableRectangle extends Rectangle {
	
	private Integer WallType = null;

	public CollidableRectangle(double width, double height, double x, double y) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		setVisible(false);
	}

	public CollidableRectangle(double width, double height, double x, double y, int wallType) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		setVisible(false);
		this.WallType = wallType;
	}
	
	public Integer getWallType() {
		return WallType;
	}

}
