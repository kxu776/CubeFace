package com.zaxxon.Networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	private String ipAddress;
	private int port;
	private InetAddress serverAddress;
	private DatagramSocket socket;
	
	
	public Client(String host, int port) {
		this.port = port;
		ipAddress = host;
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
