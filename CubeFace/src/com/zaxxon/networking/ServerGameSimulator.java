package com.zaxxon.networking;

import java.net.InetAddress;
import java.util.Iterator;

import com.zaxxon.client.MainGame;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.enemies.Enemy;

public class ServerGameSimulator extends Thread{
	Server server;
	boolean run = false;
	public ServerGameSimulator(Server server) {
		this.server = server;
	}
	int serverSize;
	public void run(){
		run = true;
		while(run) {
			
			if(server.clients.size()<2) {
				continue;
			}
			try {
				serverSize = MainGame.enemiesList.size();
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			for (Iterator<Enemy> it = MainGame.enemiesList.iterator(); it.hasNext(); ) {
				Enemy e = MainGame.enemiesList.iterator().next();
				if(e.isAlive()) {
					sendZombies(e.getPosition());
				}			    
			}
			
		}
		server.close();
	}
	
	private void sendZombies(Vector2 vec) {
		String x = ""+ vec.x;
		String y = ""+ vec.y;
		String amount = ""+serverSize;
		String zombie = "/z/"+x+"/"+y+"/"+serverSize+"/";
		server.sendToAll(zombie.getBytes());
	}
	
	
	//TODO: implement a way of sending zombies to other players that is synchronised.
		// Perhaps simulate game on server.
		private void distrubuteZombies(int port,InetAddress address) {
			while(MainGame.enemiesList.iterator().hasNext()){
				Enemy e = MainGame.enemiesList.iterator().next();
				sendZombies(e.getPosition());
			}
		}

}
