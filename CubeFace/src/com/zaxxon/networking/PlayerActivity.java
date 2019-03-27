package com.zaxxon.networking;

import java.util.HashMap;

public class PlayerActivity extends Thread {
	private Server server;

	public PlayerActivity(Server server) {
		this.server = server;
	}

	public void run() {
		while(server.isRunning() == true) {
		try {
			sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		for (HashMap.Entry<Integer, ServerClient> c : server.clients.entrySet()) {
			if (c.getValue().getInactive() == 5) {
				System.out.println("Inactive player "+ c.getValue().getPort());
				server.disconnect(c.getKey(), c.getValue().getAddress());
			} else {
				server.send(("/p/").getBytes(), c.getValue().getAddress(), c.getKey());
				c.getValue().increaceInactive();
			}
		}
		}
		return;
	}
}
