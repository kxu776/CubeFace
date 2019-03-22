package com.zaxxon.networking;

import java.net.InetAddress;
import java.util.ListIterator;
import java.util.UUID;

import com.zaxxon.client.MainGame;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.enemies.Enemy;

public class ServerGameSimulator extends Thread {
	Server server;
	InetAddress serverAddress = null;
	int serverPort;
	boolean run = false;

	public ServerGameSimulator(Server server, int serverPort, InetAddress serverAddress) {
		this.server = server;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		run = true;
		MainGame.multiplayer = true;
		MainGame.reset();
		MainGame.startMP();
	}

	int serverSize;

	public void run() {
		while (run) {

			for (ListIterator<Enemy> iter = MainGame.enemiesList.listIterator(); iter.hasNext();) {
				Enemy e = iter.next();
				if (e.isAlive() && e.getId() != null && run == true) {
					sendZombies(e.getPosition(), e);
				} else if (e.isAlive() && run == true) {
					String id = UUID.randomUUID().toString();
					e.setId(id);
					String spawn = "/s/" + e.getPosition().toString() + "/" + id + "/";
					spawnZombies(spawn);
				}
				break;
			}
		}
	}

	private void spawnZombies(String spawn) {
		server.sendToAll(spawn.getBytes());
	}

	private void sendZombies(Vector2 vec, Enemy e) {
		String ID = e.getId();
		String x = "" + vec.x;
		String y = "" + vec.y;
		String amount = "" + serverSize;
		String zombie = "/z/" + x + "/" + y + "/" + ID + "/";
		server.sendToAll(zombie.getBytes());
	}

	// TODO: implement a way of sending zombies to other players that is
	// synchronised.
	// Perhaps simulate game on server.
	private void distrubuteZombies(int port, InetAddress address) {
		while (MainGame.enemiesList.iterator().hasNext()) {
			Enemy e = MainGame.enemiesList.iterator().next();
			sendZombies(e.getPosition(), e);
		}
	}

}
