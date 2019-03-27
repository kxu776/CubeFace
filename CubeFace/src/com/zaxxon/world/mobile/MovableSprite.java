package com.zaxxon.world.mobile;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;

import java.util.LinkedHashMap;

/**
 * Abstract representation of a mobile sprite.
 */
public abstract class MovableSprite extends Sprite {
	protected double velocityX = 0;
	protected double velocityY = 0;
	protected double health = 0;

	public Player lastHitReceived;

	public boolean controllable;
	protected boolean isAlive;

	public enum FacingDir {
		up, down, left, right, upRight, upLeft, downRight, downLeft
	}

	/**
	 * Default class constructor
	 */
	public MovableSprite() {
	}

	// methods for player

	/**
	 * Sets the position coordinates of this sprite within the game map
	 *
	 * @param pos vector2 object containing a set of positional coordinates
	 */
	public void setPosition(Vector2 pos) {

		this.setX(pos.x);
		this.setY(pos.y);
	}

	/**
	 * Sets the health value of this object
	 *
	 * @param health a double representing the health of this object
	 */
	public void setHealth(double health) {
		this.health = health;
	}

	/**
	 * Returns a vector2 object containing the positional coordinates of this object
	 *
	 * @return the position of this object
	 */
	public Vector2 getPosition() {

		return new Vector2(this.getX(), this.getY());
	}

	/**
	 * Changes the position of this object using vector addition
	 *
	 * @param v delta vector for movement
	 */
	public void translate(Vector2 v) {

		this.setX(this.getX() + v.x);
		this.setY(this.getY() + v.y);
	}

	public void update(double time) {
		this.setX(this.getX() + velocityX * time);
		this.setY(this.getY() + velocityY * time);
	}

	/**
	 * Decrements the health of this object, representing damage being inflicted
	 * upon this object.
	 *
	 * If the health falls to 0, the object is declared dead (to be removed from the
	 * game). The health of this object should never be negative.
	 *
	 * @param damage the amount of damage to be taken by this object
	 */
	public void takeDamage(double damage) {
		if (health - damage > 0.0) {
			health -= damage;
		} else {
			health = 0.0;
			isAlive = false;
		}
		assert health >= 0.0;
	}

	/**
	 * Decrements the health of this object while recording which player inflicted
	 * said damage. Represents damage being inflicted upon this object by a bullet
	 *
	 * @param damage the amount of damage to be taken by this object
	 * @param player the player from which the damaging bullet originated
	 */
	public void takeDamage(double damage, Player player) {
		lastHitReceived = player;
		takeDamage(damage);
	}

	/**
	 * Increments the health of this object
	 *
	 * @param healing the amount of healing to be done to this object
	 */
	public void heal(double healing) {

		health = Math.min(100, health + healing);
	}

	/**
	 * Returns the health value of this object
	 *
	 * @return the health of this object
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * Returns a boolean representing whether this object is alive or not.
	 *
	 * @return true if the enemy is still alive, otherwise false
	 */
	public Boolean isAlive() {
		return isAlive;
	}

	/**
	 * Returns a String containing the concatenation of this object's attributes
	 *
	 * @return concatenated string of the attribute values of this object
	 */
	public String toString() {
		return super.toString() + ", Velocity: [" + velocityX + "," + velocityY + "]";
	}

	/**
	 * Returns a collection of key-value pairs containing this object's attributes
	 * and their associated field names as values and keys respectively
	 *
	 * @return LinkedHashMap<String, Object> of this object's attributes.
	 */
	@Override
	public LinkedHashMap<String, Object> getAttributes() {
		LinkedHashMap<String, Object> attributes = super.getAttributes();
		attributes.put("velocityX", velocityX);
		attributes.put("velocityY", velocityY);
		attributes.put("Health", health);

		return attributes;
	}
}
