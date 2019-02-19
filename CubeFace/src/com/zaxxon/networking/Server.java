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
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	private final int serverPort;
	private DatagramSocket serverSocket;
	private boolean listening = false;
	private Thread listenThread;
	private int MAX_PACKET_SIZE = 1024;
	private byte[] data = new byte[MAX_PACKET_SIZE];
	public  HashMap<Integer,ServerClient> clie = new HashMap<>();
	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayInputStream bais;
	private int ID = 1;
	private boolean waiting = false;

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

	private synchronized void broadcastPlayers(DatagramPacket packet) {
		// editObj(packet);
//		
//		for (ServerClient c : clients) {
//			send(packet.getData(),c.getAddress(),c.getPort());
//		}	
		for (HashMap.Entry<Integer, ServerClient> c : clie.entrySet()) {
			if (packet.getPort() != c.getKey()) {	
				send(packet.getData(),(c.getValue()).getAddress(),c.getKey());
			}
		}	
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		
		// less than 2 clients then just wait on connection packets only 	

		if (new String(data).trim().startsWith("/C/")) {
			System.out.println("------------");
			System.out.println("New Player ");
			System.out.println(address.getHostAddress() + " : " + port);
			System.out.println("------------");
			
			clie.put(packet.getPort(),(new ServerClient(packet.getAddress(), packet.getPort(), ID)));			
			System.out.println("Sending connection to clients");
			send("/c/Connected".getBytes(), address, port);
			ID++;
			}
		
		if (clie.size() < 2) {
			if (waiting) {
				return;
			}
			System.out.println("Waiting on players...");
			send("/s/Waiting for one more player...".getBytes(),address,port);
			waiting = true; 
			return;
		}
		else {
			waiting = false;
			broadcastPlayers(packet);
		//	serverSocket.close();
			// System.out.println("Closed socket?");
			// listening = false;
		} 
	}

	public InetAddress getServerIP() {
		return serverSocket.getInetAddress();
	}

	public void close() {
		serverSocket.close();
	}
}
