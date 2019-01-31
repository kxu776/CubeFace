package com.zaxxon.Networking;

import java.io.IOException;
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
	
	
	public Client(String host, int port) {
		//port refers to port of the server
		this.port = port;
		ipAddress = host;
	}
	public void run() {
		
		connect();
		
		while(true) {
			DatagramPacket packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
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
		
	}
	
	private void sendConnectionPacket() {
		byte[] data = "ConnectionPacket".getBytes();
		send(data);
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
