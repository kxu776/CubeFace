package com.zaxxon.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {

	private DatagramSocket serverSocket;
	private Thread listenThread;
	private boolean listening = false;
	private final int SERVER_PORT;
	private String SERVER_IP;
	private int MAX_PACKET_SIZE = 1024;
	private byte[] data = new byte[MAX_PACKET_SIZE];

	protected ConcurrentHashMap<Integer, ServerClient> clients = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, byte[]> lastKnownPos = new ConcurrentHashMap<>();

	@SuppressWarnings("unused")
	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayInputStream bais;
	private InetAddress ServerAddress;
	// private ServerGameSimulator simulator;

	public Server(int serverPort) {
		this.SERVER_PORT = serverPort;
	}

	public void run() {
		try {
			System.out.println("Server started on port " + SERVER_PORT);
			serverSocket = new DatagramSocket(SERVER_PORT);
			ServerAddress = InetAddress.getLocalHost();
			SERVER_IP = ServerAddress.toString();
			System.out.println(SERVER_IP);
			listening = true;
			// simulator = new ServerGameSimulator(this, SERVER_PORT, ServerAddress);

			listenThread = new Thread(new Runnable() {
				public void run() {
					listen();
				}
			});
			listenThread.start();
			// simulator.start();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Server can't be started, IP isnt known");
			e.printStackTrace();
		}
	}

	public void send(byte[] data, InetAddress address, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			if (listening == true) {
				serverSocket.send(packet);
				Thread.sleep(15);
			}

		} catch (IOException e) {
		} catch (InterruptedException e) {
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

	// Read in the info and set the ID corresponding to ServerClient info.
	private byte[] editObj(byte[] bs, String ID) {
		try {
			bais = new ByteArrayInputStream(bs);
			in = new ObjectInputStream(bais);
			ClientSender data = (ClientSender) in.readObject();
			data.setID(ID);
			// simulator.mg.inputUpdateQueue.add(data);
			bais.close();
			in.close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				out = new ObjectOutputStream(baos);
				out.writeObject(data);
				out.flush();
				byte[] playerinfo = baos.toByteArray();
				// Update last Known position server side.
				if (lastKnownPos.containsKey(ID)) {
					lastKnownPos.remove(ID);
					lastKnownPos.put(ID, playerinfo);
				}
				out.close();
				baos.close();
				return playerinfo;
			} catch (IOException e) {
				System.out.println(bs.toString());
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(new String(bs));
			e.printStackTrace();
		}
		return bs;
	}

	protected void sendToAll(byte[] b) {
		for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
			send(b, c.getValue().getAddress(), c.getKey());
		}
	}

	protected void sendToRelevant(byte[] b, int port, InetAddress address) {
		for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
			if (port == c.getKey()) {
				if (address.equals(c.getValue().getAddress())) {
					continue;
				}
			}
			send(b, c.getValue().getAddress(), c.getKey());
		}
	}

	private void broadcastPlayers(DatagramPacket packet) {
		int port = packet.getPort();
		InetAddress address = packet.getAddress();
		ServerClient associatedID;
		byte[] playerAttributes;

		for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {

			// If only one player exists in the server
			// we store their current location

			if (port == c.getKey()) {
				if (address.equals(c.getValue().getAddress())) {
					associatedID = c.getValue();

					// Setup its ID and get ready to send to other players

					playerAttributes = editObj(packet.getData(), associatedID.getID());
					if (!lastKnownPos.containsKey(c.getValue().getID())) {
						lastKnownPos.put(c.getValue().getID(), playerAttributes);
					}
					sendToRelevant(playerAttributes, port, address);
					break;
				}
			}
		}

		// Can't have the port of the client sending the info be the same as the port
		// from a different machine
		// each client has a unique IP
		// so we have to account for that and make sure we only send info to those who
		// need it.

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
			String id = UUID.randomUUID().toString().trim();

			clients.put(packet.getPort(),
					(new ServerClient(action.substring(3), packet.getAddress(), packet.getPort(), id)));

			send(("/c/Connected/" + id + "/").getBytes(), address, port);
			// No need to send last known position to only one player.
			if (clients.size() < 2) {
				return;
			}
			for (HashMap.Entry<String, byte[]> c : lastKnownPos.entrySet()) {
				send(c.getValue(), address, port);
			}
			return;
		}

		else if (action.startsWith("/d/")) {
			for (HashMap.Entry<Integer, ServerClient> c : clients.entrySet()) {
				if (port == c.getKey() && address.equals(c.getValue().getAddress())) {
					String[] disconnectIParray = c.getValue().getAddress().toString().split("/");
					String disconnectIP = disconnectIParray[1];
					// Should host disconnect we remove everyone from multiplayer
					if (c.getValue().getAddress().equals(ServerAddress) || disconnectIP.equals("localhost")
							|| disconnectIP.equals("127.0.0.1")) {
						sendToRelevant("/b/".getBytes(), port, address);
						close();
						return;
					}
					// Otherwise we tell everyone who disconnected and remove them.
					String idToRemove = c.getValue().getID();
					String disconnectIT = "/d/" + idToRemove + "/";
					lastKnownPos.remove(c.getValue().getID());
					clients.remove(c.getKey(), c.getValue());
					byte[] disconnected = disconnectIT.getBytes();
					sendToRelevant(disconnected, port, address);
					return;
				}
			}
		}

		// If we don't have enough players we just update the position of who is
		// currently connected.
		if (clients.size() < 2) {
			String currentPlayer = clients.get(port).getID();
			updatePos(packet.getData(), currentPlayer);
			return;
		}

		else {
			// Game starts
			broadcastPlayers(packet);
		}
	}

	// Could be used by UI to show user what to enter.
	public String getServerIP() {
		return SERVER_IP;
	}

	// Update the players position
	private void updatePos(byte[] b, String ID) {
		if (!lastKnownPos.containsKey(ID)) {
			lastKnownPos.put(ID, b);
			System.out.println(lastKnownPos.size());
		} else {
			editObj(b, ID);
		}
	}

	public boolean isRunning() {
		return listening;
	}

	// Close the server.
	public void close() {
		try {
			listening = false;
			listenThread.interrupt();
			// simulator.run = false;
			// simulator.interrupt();
			serverSocket.setSoTimeout(1000);
			serverSocket.close();

		} catch (SocketException e) {
		}
	}

	// Send the last known positions of any other player
	// to the one who connected.

	// Client -> Server
	// Server Checks how many current players
	// When Server has 2 or more connections we start broadcasting
	// Server Sends relevant info to player
	// If new client connects midgame we iterate through hashmap
	// send the last known co-ordinates to that player of each player
}
