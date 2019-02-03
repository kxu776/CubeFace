package com.zaxxon.networking;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

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

	//Parses hashmap of sprite attributes to string. Can then be converted to packet via byte array.
	public static String toString(LinkedHashMap<String, Object> attributes){
		String outputString = "";
		for(Map.Entry<String, Object> value: attributes.entrySet()){
			outputString += String.valueOf(value.getValue());
		}
		return outputString;
	}

	//Test method
	public static void main(String[] args){
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("test", new Integer(17));
		map.put("test1", new String("test1"));
		map.put("test2", new Character('c'));
		System.out.println(toString(map));
	}
	
}
