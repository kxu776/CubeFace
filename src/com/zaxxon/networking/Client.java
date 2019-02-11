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
	private ClientSender client;
	private boolean running = false;

	public Client(String host, int port) {
		// port refers to port of the server
		this.port = port;
		this.ipAddress = host;
	}

	public void run() {
		running = true;
		connect();
		while (running) {
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				// Expects a player object
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			getPlayerObj(packet);
			sendPlayerObj();
		}
	}

	public void connect() {
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
		byte[] data = "ConnectionPacket".getBytes();
		send(data);
	}

	private void getPlayerObj(DatagramPacket packet) {
		System.out.println("Incoming from server......");
		if (packet.getData() != null) {
			String message = new String (packet.getData());
			
			if (message.trim().equalsIgnoreCase("Connected")) { 
				System.out.println("Server >: " + message);
				return;
			}
			
			else {
				try {
					System.out.println("Object recieved ");
					bais = new ByteArrayInputStream(packet.getData());
					in = new ObjectInputStream(bais);
					ClientSender data = (ClientSender) in.readObject();
					System.out.println("Health is: " +  data.getHealth());
					System.out.println("Object tested, closing socket");
					//socket.close();
				//	running = false;
//					try {
//						bais.close();
//						in.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
			
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}
				
		else {
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
	}

	private void sendPlayerObj() {
		client = new ClientSender(100, 100, 100);
		baos = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(client);
			out.flush();
			byte[] playerinfo = baos.toByteArray();
			send(playerinfo);
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

}
