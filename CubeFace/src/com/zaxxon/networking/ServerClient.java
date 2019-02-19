package com.zaxxon.networking;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final int ID;

	public ServerClient(InetAddress address, int port, final int ID) {
		
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
		String id = ""+ID;
		return id;
	}

}
