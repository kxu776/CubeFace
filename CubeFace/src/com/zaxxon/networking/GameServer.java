package com.zaxxon.networking;

public class GameServer {
	int port;

	public GameServer(int port) {
		this.port = port;
	}

	public static void main(int port) {
		GameServer gs = new GameServer(port);
		Server server = new Server(gs.port);

		server.run();
		System.out.println("Running");
	}

}
