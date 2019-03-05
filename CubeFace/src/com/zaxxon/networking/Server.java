package com.zaxxon.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class Server {

	private final int serverPort;
	private DatagramSocket serverSocket;
	private boolean listening = false;
	private Thread listenThread;
	private int MAX_PACKET_SIZE = 1024;
	private byte[] data = new byte[MAX_PACKET_SIZE];
	public static HashMap<Integer, ServerClient> clients = new HashMap<>();
	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayInputStream bais;
	private boolean waiting = false;

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

	private byte[] editObj(byte[] bs, String ID) {

		try {
			bais = new ByteArrayInputStream(bs);
			in = new ObjectInputStream(bais);
			ClientSender data = (ClientSender) in.readObject();
			data.setID(ID);
			bais.close();
			in.close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				out = new ObjectOutputStream(baos);
				out.writeObject(data);
				out.flush();
				byte[] playerinfo = baos.toByteArray();
				out.close();
				baos.close();
				return playerinfo;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(new String(bs));
			e.printStackTrace();
		}
		return bs;
	}

	private void broadcastPlayers(DatagramPacket packet) {
		for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
			// Can't have the port of the client sending the info be the same as the port
			// from a different machine
			// each client has a unique IP
			// so we have to account for that and make sure we only send info to those who
			// need it.
			if (packet.getPort() != c.getKey() && packet.getAddress() != c.getValue().getAddress()) {
				byte[] b = editObj(packet.getData(), c.getValue().getID());				
				send(b, (c.getValue()).getAddress(), c.getKey());
			}
		}
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		String action = new String(data);

		if (action.startsWith("/C/")) {
			System.out.println("------------");
			System.out.println("New Player ");
			System.out.println(address.getHostAddress() + " : " + port);
			System.out.println("------------");

			clients.put(packet.getPort(),
			(new ServerClient(action.substring(3),
			packet.getAddress(),
			packet.getPort(),
			UUID.randomUUID().toString())));
			
			send("/c/Connected".getBytes(), address, port);
		}

		if (action.startsWith("/b/")) {
			broadcastGen(packet);
		}

		if (action.startsWith("/d/")) {
			for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
				if (port == c.getKey() && address != c.getValue().getAddress()) {
					clients.remove(c.getKey(), c.getValue());
					return;
				}
			}
		}

		if (clients.size() < 2) {
			if (waiting) {
				return;
			}
			System.out.println("Waiting on players...");
			send("/s/Waiting for one more player...".getBytes(), address, port);
			waiting = true;
			return;
		} else {
			if (action.startsWith("/C/")||action.startsWith("/b/")) {
				return;
			}
			waiting = false;
			broadcastPlayers(packet);
		}
	}

	private void broadcastGen(DatagramPacket p) {
		for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
			if (p.getPort() != c.getKey() && p.getAddress() != c.getValue().getAddress()) {
				p.setPort(c.getKey());p.setAddress(c.getValue().getAddress());
				try {
					serverSocket.send(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public InetAddress getServerIP() {
		return serverSocket.getInetAddress();
	}

	public void close() {
		listening = false;
		serverSocket.close();
		try {
			listenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
