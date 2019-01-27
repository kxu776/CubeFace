package com.zaxxon.Networking;

import java.io.IOException;
import java.net.*;

public class Server {

	private final int serverPort;
	private DatagramSocket serverSocket;
	private boolean listening = false;
	private Thread listenThread;
	private int MAX_PACKET_SIZE = 1024;
	private byte[]  data = new byte[MAX_PACKET_SIZE * 10];

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
		//
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listen() {
		while (listening) {
			//Used to receive and then handle the packets
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
		
		System.out.println("--------");
		System.out.println("PACKET " );
		System.out.println(address.getHostAddress() + " : " + port);
		System.out.println("--------");


	}
}


