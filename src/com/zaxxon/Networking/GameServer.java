package com.zaxxon.Networking;

public class GameServer {

	public static void main(String[] args) {
		Server server =  new Server(4444);
		server.start();
	}
	
}
