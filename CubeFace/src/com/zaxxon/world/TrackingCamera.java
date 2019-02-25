package com.zaxxon.world;

public class TrackingCamera extends Thread {
	
	double positionX = 0;
	double positionY = 0;
	double scaleX = 1;
	double scaleY = 1;
	private Sprite spriteToFollow;
	private boolean running = false;
	
	public TrackingCamera(Sprite s) {
		spriteToFollow = s;
	}
	
	public void run() {
		running = true;
		while(running) {
			positionX = spriteToFollow.getX();
			positionY = spriteToFollow.getY();
		}
		running = false;
	}
	
	public void kill() {
		running = false;
	}
	
	public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
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
