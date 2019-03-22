package com.zaxxon.networking;

public class GameServer {
	static int port;
	public GameServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		Server server =new Server(port);
		server.start();
	}

}
