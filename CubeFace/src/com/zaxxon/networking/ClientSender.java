package com.zaxxon.networking;

import java.io.Serializable;
import java.net.InetAddress;

public class ClientSender implements Serializable{
	public double x;
	public double y;
	public double health;
	public InetAddress ipAddress;

	public int port;
	
	public ClientSender(double d, double e, double health) {
		this.x = d;
		this.y = e;
		this.health = health;
	}
	
	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}
	
}