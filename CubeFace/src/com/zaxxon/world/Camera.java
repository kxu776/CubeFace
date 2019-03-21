package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.scene.shape.Rectangle;

/**
 * An abstract class used to implement 2D cameras
 * 
 * @author philip
 *
 */
public abstract class Camera {

	/**
	 * The X position of the Camera
	 */
	protected double positionX = 0;
	/**
	 * The Y position of the Camera
	 */
	protected double positionY = 0;
	/**
	 * The X scale of the Camera
	 */
	protected double scaleX = 1;
	/**
	 * The Y scale of the Camera
	 */
	protected double scaleY = 1;

	/**
	 * Usage: call to update the viewport of the world
	 */
	public abstract void update();

	/**
	 * Sets the X and Y positions of the Camera
	 * 
	 * @param positionX The new X position of the Camera
	 * @param positionY The new Y position of the Camera
	 */
	public void setPosition(double positionX, double positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	/**
	 * @return The X position of the Camera
	 */
	public double getPositionX() {
		return positionX;
	}

	/**
	 * Sets the X position of the Camera
	 * 
	 * @param positionX The new X position of the Camera
	 * 
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * @return The Y position of the Camera
	 */
	public double getPositionY() {
		return positionY;
	}

	/**
	 * Sets the Y position of the Camera
	 * 
	 * @param positionY The new Y position of the Camera
	 * 
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	/**
	 * Sets the X and Y scale of the Camera
	 * 
	 * @param scaleX The new X scale of the Camera
	 * @param scaleY The new Y scale of the Camera
	 */
	public void setScale(double scaleX, double scaleY) {
		setScaleX(scaleX);
		setScaleY(scaleY);
	}

	/**
	 * @return The X scale of the Camera
	 */
	public double getScaleX() {
		return scaleX;
	}

	/**
	 * Sets the X scale of the Camera
	 * 
	 * @param scaleX The new X scale of the Camera
	 * 
	 */
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @return The Y scale of the Camera
	 */
	public double getScaleY() {
		return scaleY;
	}

	/**
	 * Sets the Y scale of the Camera
	 * 
	 * @param scaleY The new Y scale of the Camera
	 * 
	 */
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * generates a Rectangle to represent the bounds of the viewing area for a
	 * camera
	 * 
	 * @return Rectangle with bounds matching the area visible to the camera
	 */
	public Rectangle getViewingBounds() {
		double minX = -MainGame.getWorld().getTranslateX();
		double width = MainGame.getRenderedScene().getWidth() / scaleX;
		double minY = -MainGame.getWorld().getTranslateY();
		double height = MainGame.getRenderedScene().getHeight() / scaleY;
		Rectangle r = new Rectangle(minX, minY, width, height);
		return r;
	}

}
