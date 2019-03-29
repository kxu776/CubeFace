package com.zaxxon.networking;
/**
 *  A separate thread thats duty is to end the game for all players.
 * @author Omar Farooq Khan
 *
 */
public class GameOver extends Thread{
	private Server server;
	
	public GameOver(Server server){
		this.server = server;
	}
	/**
	 * Send the command to end the game.
	 */
	public void run() {
		try {
			server.sendToAll("/e/".getBytes());
			sleep(100);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
		}
	}

}
