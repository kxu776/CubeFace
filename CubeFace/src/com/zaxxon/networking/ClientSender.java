package com.zaxxon.networking;

import java.io.Serializable;

/**
 * This class is the object that is sent over the network
 * containing necessary information about the player.
 *  
 * @author Omar Farooq Khan
 *
 */

public class ClientSender implements Serializable{
	static final long serialVersionUID = 42L;
	public String name;
	public int pos;
	public int currWep;

	public double x;
	public double y;
	public double health;
	public boolean shoot = false;
	public boolean alive = true;
	private String ID;
	
	/**
	 * Constructor - Set up the player to with the info to be sent. 
	 * @param x x coordinate of the player.
	 * @param y y coordinate of the player.
	 * @param health
	 */
	public ClientSender(double x, double y, double health) {
		this.x =	 x;
		this.y = y;
		this.health = health;
	}
	
	/**
	 * Get the x position of ClientSender that is currently stored.
	 * @return double x position of player.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Set the x position of the ClientSender.
	 * @param x Set the x position
	 * @return nothing
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Get the y position of ClientSender.
	 * @return double y position of ClientSender.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set the y position of the ClientSender.
	 * @return nothing
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return double get the current health of the ClientSender.
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * @param health Set the health of the ClientSender.
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * @return String ID of the ClientSender.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param ID Set the ID of the ClientSender.
	 */
	public void setID(String ID) {
		this.ID = ID;
	}
	
	/**
	 * @return int The number of which gun is currently selected.
	 */
	public int getCurrWep() {
		return currWep;
	}
	
}
