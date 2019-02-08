package com.zaxxon.world;

public class Camera {
	
	private double positionX = 0;
	private double positionY = 0;
	private double scaleX = 1;
	private double scaleY = 1;
	
	public Camera() {
		
	}

	public void setPosition(double positionX, double positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

}