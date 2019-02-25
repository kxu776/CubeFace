package com.zaxxon.world.mobile;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;

import java.util.LinkedHashMap;

public abstract class MovableSprite extends Sprite {
	private double velocityX;
	private double velocityY;
	private double movementSpeed;
	private double health;

	public boolean controllable;
	protected boolean isAlive;

	public enum FacingDir {
		up, down, left, right
	}

	public MovableSprite() {
		velocityX = 0.0;
		velocityY = 0.0;
		movementSpeed = 0.0;
		health = 0.0;

	}
	
	//methods for player
	
	public void setPosition(Vector2 pos) {
		
		this.setX(pos.x);
		this.setY(pos.y);
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public Vector2 getPosition() {
		
		return new Vector2 (this.getX(), this.getY());
	}
	
	public void translate(Vector2 v) {
		
		this.setX(this.getX() + v.x);
		this.setY(this.getY() + v.y);
	}



	public void update(double time) {
		this.setX(this.getX() + velocityX * time);
		this.setY(this.getY() + velocityY * time);
	}

	public void takeDamage(double damage) {
		if(health-damage>0.0){
			health -= damage;
		}else{
			health = 0.0;
			isAlive = false;
		}
	}

	public void heal(double healing) {

		health += healing;
	}

	public double getHealth() {
		return health;
	}



	public Boolean isAlive(){
		return isAlive;
	}



	public String toString() {
		return super.toString() + ", Velocity: [" + velocityX + "," + velocityY + "]";
	}

	@Override
	public LinkedHashMap<String, Object> getAttributes() {
		LinkedHashMap<String,Object> attributes =  super.getAttributes();
		attributes.put("velocityX", velocityX);
		attributes.put("velocityY", velocityY);
		attributes.put("Health", health);

		return attributes;
	}
}
