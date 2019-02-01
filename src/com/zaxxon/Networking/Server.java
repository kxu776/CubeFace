package com.zaxxon.Networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

	private final int serverPort;
	private DatagramSocket serverSocket;
	private boolean listening = false;
	private Thread listenThread;
	private int MAX_PACKET_SIZE = 1024;
	private byte[]  data = new byte[MAX_PACKET_SIZE * 10];
	private HashMap<InetAddress,Integer> clients = new HashMap();
	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;

	public Server(int serverPort) {
		this.serverPort = serverPort;
	}

	public void start() {
		try {	
			serverSocket = new DatagramSocket(serverPort);
			listening = true;
			listenThread = new Thread(new Runnable() {
				public void run() {
					listen();
				}
			});
			listenThread.start();
			//Used to wait till we receive something
		try {
			listenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void send(byte[] data, InetAddress address, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(data,MAX_PACKET_SIZE);
			try {
				serverSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (clients.isEmpty()) {
			process(packet);	
		}
		else {
			
			System.out.println("Sending Received Obj to client");

			broadcastPlayers(packet);
			
			System.out.println("Packet Received");
			serverSocket.close();
			System.out.println("Closed socket?");
			listening = false;
			
			}
		}
	}
	
	private void broadcastPlayers(DatagramPacket packet) {
		try {
			baos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(baos);
			baos.flush();
			byte [] bytes = baos.toByteArray();
			out.flush();
			
			//DatagramPacket playerPacket = new DatagramPacket(bytes, bytes.length);
			
			int tempPort = packet.getPort();
			InetAddress tempIP = packet.getAddress();
			
			System.out.println("sending player object");
			send(bytes, tempIP, tempPort);
			
			out.close();
			baos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		
		System.out.println("--------");
		System.out.println("PACKET " );
		System.out.println(address.getHostAddress() + " : " + port + " " + new String(packet.getData()));
		System.out.println("--------");
		
		clients.put(address, port);
		
		send("Connected".getBytes(),address, port);
		
	}
	
	public InetAddress getServerIP() {
		return serverSocket.getInetAddress();
	}
	
	public void close() {
		serverSocket.close();
	}
}


