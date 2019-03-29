package com.zaxxon.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.zaxxon.client.MainGame;

public class Client extends Thread {

	private String ipAddress;
	private int port;
	private InetAddress serverAddress;
	private DatagramSocket socket;
	private int MAX_PACKET_SIZE = 512;
	private String ID = null;
	private byte[] data = new byte[MAX_PACKET_SIZE];
	private boolean running = false;
	private String player;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	private Thread sendThread;
	
	/**
	 * This class is the thread that involves sending
	 * necessary information about the player over the network.
	 *  
	 * @author Omar Farooq Khan
	 *
	 */
	
	/**
	 * Constructor to setup the client and where they will connect to.
	 * @param host IP of the server
	 * @param port Port number of the server
	 * @param player Player name
	 */
	public Client(String host, int port, String player) {
		// port refers to port of the server
		this.port = port;
		this.ipAddress = host;
		this.player = player;		
	}
		
	/**
	 * Packets are recieved and processed.
	 */
	public void run() {
		connect();
		while (running) {
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
			}
			process(packet);
		}
		MainGame.multiplayer = false;
	}
		
	/**
	 * 
	 */
	private void connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			return;
		}
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		sendConnectionPacket();
	}

	private void sendConnectionPacket() {
		byte[] data = ("/C/" + player).getBytes();
		send(data);
		running = true;
	}

	/**
	 * Method to process information received by the server and handle it accordingly.
	 * @param packet Data sent by the server.
	 */
	private void process(DatagramPacket packet) {
		String message = new String(packet.getData()).trim();
		// Server confirms our connection and gives us an ID.
		if (message.startsWith("/c/")) {
			message = message.substring(3);
			String[] messID = message.split("/");
			ID = messID[1];			
			return;
		}
		// Server tells us what guns are due to spawn and where.
		else if(message.startsWith("/s/")) {
			message = message.substring(3, message.length());
			MainGame.weaponQueue.add(message);
			return;
		}
		// Player from the game has disconnected and we have to remove him with his specified ID.
		else if(message.startsWith("/d/")) {
			String[] throwAwayPlayer = message.split("/");
			System.out.println(throwAwayPlayer[2]);

			if(MainGame.getSprite(throwAwayPlayer[2])== null) {
				return;
			}
			MainGame.getSprite(throwAwayPlayer[2]).delete();
			MainGame.getSpriteList().remove(MainGame.getSprite(throwAwayPlayer[2].trim()));
			MainGame.removeFromGame(MainGame.getSprite(throwAwayPlayer[2]));
			MainGame.play.remove(throwAwayPlayer[2].trim());
			return;
		}
		// Host has disconnected so we must disconnect ourselves.
		else if(message.startsWith("/b/")) {
				MainGame.multiplayer = false;
				try {
					running = false;
					MainGame.removeAllMp();
				}
				finally {
					socket.close();		
				}
				return;
		}
		// Game has finished 
		else if(message.startsWith("/e/")) {
			MainGame.getPlayer().end = true;
			try {
				sleep(300);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			MainGame.stop();
			close();
			return;
		}
		
		if
		(message.startsWith("/C/")){
			return;
		}

		else	
			try {
				// Here is where we should update the client about other players.
				bais = new ByteArrayInputStream(packet.getData());
				in = new ObjectInputStream(bais);
				ClientSender data = (ClientSender) in.readObject();	
				MainGame.inputUpdateQueue.add(data);
			} catch (Exception e) {
				System.out.println(packet.getData().toString());
				System.out.println(message);
			}
		}
	
	/**
	 * Method where clients
	 * @param object The Client sender object containing player info to be sent.
	 */

	public void sendPlayerObj(ClientSender object) {
		if(ID!=null) {
		object.name = player;
		try {
			baos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(baos);
		
			out.writeObject(object);
			out.flush();
			byte[] playerinfo = baos.toByteArray();
			send(playerinfo);
			
			out.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		}	
	}
	
	/**
	 * Method to send a disconnection packet to the server 
	 * and close the client thread.
	 */
	public void disconnect() {
		send("/d/".getBytes());
		running = false;
		close();
	}
	/**
	 * Method to close all open streams and close
	 *  the port that the client thread used.
	 */
	private void close(){
		running = false;
		try {
			if ((in == null)){
				socket.close();		
				if(bais == null) {
					socket.close();		
					return;
				}
				return;
			}
			in.close();
			bais.close();
			socket.close();		
		} catch (IOException e) {
		}
	}
	
	/**
	 * Sends specific data to the server.
	 * @param data the data to be sent
	 */
	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			sleep(25);
			socket.send(packet);
		} catch (IOException e) {
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	// Unused method.
	public void sendThread(final byte[] data) {
		sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
				try {
					if (running) {
						sleep(25);
						socket.send(packet);
					}
				} catch (IOException e) {
				} catch (InterruptedException e) {
				}
			}
		});
		sendThread.start();
	}
}
