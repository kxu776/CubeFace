package com.zaxxon.networking;


import com.zaxxon.client.MainGame;
import com.zaxxon.world.Sprite;

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
import java.util.LinkedList;
import java.util.Timer;




public class Client extends Thread {
	
	private String ipAddress;
	private int port;
	private InetAddress serverAddress;
	private DatagramSocket socket;
	private int MAX_PACKET_SIZE = 1024;

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
				e.printStackTrace();
			}

			getPlayerObj(packet);
		}
	}
	
	public void connect(){
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
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
	}


	private void getPlayerObj(DatagramPacket packet) {
		System.out.println("Incoming from server......");

		String message = new String(packet.getData());

		if (message.trim().startsWith("/c/")) {
			System.out.println("Server >: " + message.substring(3));
			return;
		}

		else if (message.startsWith("/s/")) {
			message = message.substring(3, message.length());
			System.out.println(message);
			return;
		}

		else 
			try {	
				
				
				
				// Here is where we should update the client.
				bais = new ByteArrayInputStream(packet.getData());
				in = new ObjectInputStream(bais);

				ClientSender data = (ClientSender) in.readObject();
				
				MainGame.inputUpdateQueue.add(data);
				//System.out.println("Health is: " + data.getHealth());
				//System.out.println("Position is:" + data.getX() + " " + data.getY());
				//System.out.println("ID is: " + data.getID());
				//50 packets per second
				Thread.sleep(20);
			
//				socket.close();
//				running = false;
//				try {
//					bais.close();
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPlayerObj(ClientSender c) {
		baos = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(c);
			out.flush();
			byte[] playerinfo =  baos.toByteArray();
			
			send(playerinfo);
			Thread.sleep(30);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		send("/d/.".getBytes());
		System.out.println("No, closing socket");
		socket.close();
		running = false;
		try {
			bais.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    public void spritesToString(LinkedList<Sprite> spriteList) {
    }
}
