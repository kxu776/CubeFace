package com.zaxxon.networking;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final String ID;
	public ServerClient(String user,InetAddress address, int port, final String ID) {
		this.name = user;
		this.address = address;
		this.port = port;
		this.ID = ID;
	}


	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getID() {
		return ID;
	}
}
