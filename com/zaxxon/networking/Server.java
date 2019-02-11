package com.zaxxon.networking;

import java.io.ByteArrayInputStream;
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
	private byte[] data = new byte[MAX_PACKET_SIZE * 10];
	private HashMap<InetAddress, Integer> clients = new HashMap();
	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayInputStream bais;

	public Server(int serverPort) {
		this.serverPort = serverPort;
	}

	public void start() {
		System.out.println("Starting the server........");
		try {
			serverSocket = new DatagramSocket(serverPort);
			listening = true;
			listenThread = new Thread(new Runnable() {
				public void run() {
					listen();
				}
			});
			listenThread.start();
			// Used to wait till we receive something
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
			DatagramPacket packet = new DatagramPacket(data, MAX_PACKET_SIZE);
			try {
				serverSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			process(packet);

		}
	}

	private void editObj(DatagramPacket packet) {
		int tempPort = packet.getPort();
		InetAddress tempIP = packet.getAddress();

		try {
			System.out.println("Object recieved ");
			bais = new ByteArrayInputStream(packet.getData());
			in = new ObjectInputStream(bais);
			ClientSender data = (ClientSender) in.readObject();
			System.out.println(data.getHealth());
			data.setHealth(75);

			sendPlayerObj(data, tempIP, tempPort);

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private void sendPlayerObj(ClientSender client, InetAddress inet, int port) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(client);
			out.flush();
			byte[] playerinfo = baos.toByteArray();
			send(playerinfo, inet, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void broadcastPlayers(DatagramPacket packet) {
		// editObj(packet);
		int tempPort = packet.getPort();
		InetAddress tempIP = packet.getAddress();
		
		//clients.remove(tempIP);
		clients.remove(tempIP, packet);
		
		for (Map.Entry<InetAddress, Integer> entry : clients.entrySet()){
			send(packet.getData(), entry.getKey(), entry.getValue());
		}
		clients.put(tempIP, tempPort);
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();

		if (clients.containsKey(address)) {
			System.out.println("Sending Received Obj to clients");
			broadcastPlayers(packet);
		//	System.out.println("Packet Received");
		//	serverSocket.close();
			//System.out.println("Closed socket?");
			//listening = false;
		} else if (new String(data).trim().equalsIgnoreCase("ConnectionPacket")) {
			System.out.println("------------");
			System.out.println("New Player ");
			System.out.println(address.getHostAddress() + " : " + port);
			System.out.println("------------");
			clients.put(address, port);
			System.out.println("Sending connection to clients");
			send("Connected".getBytes(), address, port);
		}

	}

	public InetAddress getServerIP() {
		return serverSocket.getInetAddress();
	}

	public void close() {
		serverSocket.close();
	}
}
