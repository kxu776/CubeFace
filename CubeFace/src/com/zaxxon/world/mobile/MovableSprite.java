package com.zaxxon.world.mobile;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;

import java.util.LinkedHashMap;

public class MovableSprite extends Sprite {
	private double velocityX;
	private double velocityY;
	private double movementSpeed;
	private double health;
	private double damage;
	public boolean controllable;

	protected enum FacingDir {
		up, down, left, right
	}

	public MovableSprite() {
		velocityX = 0.0;
		velocityY = 0.0;
		movementSpeed = 0.0;
		health = 0.0;
		damage = 0.0;
	}
	
	//methods for player
	
	public void setPosition(Vector2 pos) {
		
		this.setX(pos.x);
		this.setY(pos.y);
	}
	
	public Vector2 getPosition() {
		
		return new Vector2 (this.getX(), this.getY());
	}
	
	public void translate(Vector2 v) {
		
		this.setX(this.getX() + v.x);
		this.setY(this.getY() + v.y);
	}
	
	//methods not used by player

	public void setVelocity(double x, double y) {
		velocityX = x;
		velocityY = y;
	}

	public void addVelocity(double x, double y) {
		velocityX += x;
		velocityY += y;
	}

	public void update(double time) {
		this.setX(this.getX() + velocityX * time);
		this.setY(this.getY() + velocityY * time);
	}

	public void takeDamage(int damage) {
		health -= damage;
	}

	public void heal(int healing) {
		health += healing;
	}

	public double getHealth() {
		return health;
	}

	public void moveLeft() {
		addVelocity(-50, 0);
	}

	public void moveUp() {
		addVelocity(0, 50);
	}

	public void moveRight() {
		addVelocity(50, 0);
	}

	public void moveDown() {
		addVelocity(0, -50);
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
		attributes.put("Damage", damage);
		return attributes;
	}
}
