package com.zaxxon.Networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class Server {

	private final int serverPort;
	private DatagramSocket serverSocket;
	private boolean listening = false;
	private Thread listenThread;
	private int MAX_PACKET_SIZE = 1024;
	private byte[]  data = new byte[MAX_PACKET_SIZE * 10];
	private ArrayList<Integer> clientList;
	private ObjectInputStream obi;
	private ByteArrayOutputStream bao;

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
			process(packet);
		}
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		clientList = new ArrayList<>();
		
		System.out.println("--------");
		System.out.println("PACKET " );
		System.out.println(address.getHostAddress() + " : " + port + " " + new String(packet.getData()));
		System.out.println("--------");
		
		clientList.add(packet.getPort());
		
		send("Connected".getBytes(),address, port);
	}
	
	public InetAddress getServerIP() {
		return serverSocket.getInetAddress();
	}
	
	public void close() {
		serverSocket.close();
	}
}


