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
	private int MAX_PACKET_SIZE = 1024;
	private String ID = null;
	private byte[] data = new byte[MAX_PACKET_SIZE];
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	private boolean running = false;
	private String player;
	

	public Client(String host, int port, String player) {
		// port refers to port of the server
		this.port = port;
		this.ipAddress = host;
		this.player = player;
		
	}
		

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
	}
		

	private void connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
		}
		sendConnectionPacket();
	}

	private void sendConnectionPacket() {
		byte[] data = ("/C/" + player).getBytes();
		send(data);
		running = true;
	}

	private void process(DatagramPacket packet) {
		String message = new String(packet.getData()).trim();
		if (message.startsWith("/c/")) {
			message = message.substring(3);
			String[] messID = message.split("/");
			ID = messID[1];			
			return;
		}
		else if(message.startsWith("/s/")) {
			message = message.substring(3, message.length());
			MainGame.weaponQueue.add(message);
			return;
		}
		
		else if(message.startsWith("/d/")) {
			String[] throwAwayPlayer = message.split("/");
			System.out.println(throwAwayPlayer[2]);

			if(MainGame.getSprite(throwAwayPlayer[2])== null) {
				System.out.println("Im not working properly");
				return;
			}
			MainGame.getSprite(throwAwayPlayer[2]).delete();
			MainGame.getSpriteList().remove(MainGame.getSprite(throwAwayPlayer[2].trim()));
			MainGame.removeFromGame(MainGame.getSprite(throwAwayPlayer[2]));
			MainGame.play.remove(throwAwayPlayer[2].trim());
			return;
		}
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
		
		if
		(message.startsWith("/C/") || message.startsWith("/b/") 
		||
		message.startsWith("/z/")  || message.startsWith("/s/")) {
			return;
		}

		else	
			try {
				// Here is where we should update the client about other players.
				bais = new ByteArrayInputStream(packet.getData());
				in = new ObjectInputStream(bais);
				ClientSender data = (ClientSender) in.readObject();	
				if(data.alive) {
					MainGame.inputUpdateQueue.add(data);
				}
				else {
					MainGame.deathQueue.add(data);
				}

			} catch (Exception e) {
				System.out.println(packet.getData().toString());
				System.out.println(message);
			}
		}
	

	public void sendPlayerObj(ClientSender c) {
		if(ID!=null) {
		c.name = player;
		try {
			baos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(baos);
		
			out.writeObject(c);
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

	public void disconnect() {
		send("/d/".getBytes());
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
	
	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
			sleep(25);
		} catch (IOException e) {
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
}
