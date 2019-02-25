package com.zaxxon.networking;

import java.io.Serializable;
import java.net.InetAddress;

public class ClientSender implements Serializable{
	static final long serialVersionUID = 42L;
	public String name;
	public double x;
	public double y;
	public double health;
	public InetAddress ipAddress;
	public boolean spawn = false;
	private int ID;
	
	public ClientSender(double d, double e, double health) {
		this.x = d;
		this.y = e;
		this.health = health;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double d) {
		this.x = d;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getHealth() {
		return health;
	}

	
	public void setHealth(double health) {
		this.health = health;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}