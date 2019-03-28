package com.zaxxon.networking;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private int deaths =0 ;
	private final String ID;
	public ServerClient(String user,InetAddress address, int port,String ID) {
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
	
	public void resetDeaths() {
		deaths = 0;
	}
	public int getDeaths() {
		return deaths;
	}
	public int incDeaths() {
		deaths+=1;
		return deaths;
	}
}
