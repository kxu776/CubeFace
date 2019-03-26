package com.zaxxon.networking;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

import com.zaxxon.world.mobile.Player;

public class ServerGameSimulator extends Thread{
	Server server;
	InetAddress serverAddress = null;
	int serverPort;
    ServerGame serverGame;
    
	boolean run = false;
	public ServerGameSimulator(Server server,int serverPort,InetAddress serverAddress) {
		this.server = server;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		run = true;
		serverGame = new ServerGame();
		serverGame.reset();
		serverGame.startMP();
	}
	int serverSize;
	public void run(){
		while(run) {
			if(server.clients.size()<2) {
				continue;
			}
			try {
				System.out.println(serverGame.play.size());
				for (ConcurrentHashMap.Entry<String, Player> players : serverGame.play.entrySet()) {
					if(!players.getValue().isAlive()) {
						String id = players.getKey();
						server.sendToAll(("/s/"+id+"/").getBytes());
					}
				}
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(run == true) {
				continue;
			}
			else {
				return;
			}
		}
	}

}
