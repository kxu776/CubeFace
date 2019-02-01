package com.zaxxon.Networking;

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
	private byte[]  data = new byte[MAX_PACKET_SIZE];
	private boolean running = false;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	private ClientSender client;
	
	public Client(String host, int port) {
		//port refers to port of the server
		this.port = port;
		this.ipAddress = host;
	}
	public void run() {
		connect();
		while(true) {
			DatagramPacket packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
				
					if (packet.getData() != null) {
						bais = new ByteArrayInputStream(packet.getData());
						in = new ObjectInputStream(bais);
						String str = (String) in.readObject();
						System.out.println(str);
					}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			 String message = new String (packet.getData());
			System.out.println("Server >: " + message);
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
		sendPlayerObj();
	}
	
	private void sendConnectionPacket() {
		byte[] data = "ConnectionPacket".getBytes();
		send(data);
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
