package com.zaxxon.Networking;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class ClientSender implements Serializable{
	public int x;
	public int y;
	public int health;
	public InetAddress ipAddress;

	public int port;
	
	public ClientSender(int x, int y, int health) {
		this.x = x;
		this.y = y;
		this.health = health;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHealth() {
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