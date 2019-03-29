package com.zaxxon.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.zaxxon.client.MainGame;
import com.zaxxon.world.pickups.PickupPoint;

/**
 * Server which is set up by the client, which handles and distributes packets
 * accordingly.
 * 
 * @author Omar Farooq Khan
 *
 */

public class Server extends Thread {

	private DatagramSocket serverSocket;
	private Thread listenThread, sendThread;
	private Thread weaponThread;
	private boolean listening = false;
	private final int SERVER_PORT;
	private String SERVER_IP;

	private int MAX_PACKET_SIZE = 512;
	private byte[] data = new byte[MAX_PACKET_SIZE];

	protected ConcurrentHashMap<String, ServerClient> clients = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, byte[]> lastKnownPos = new ConcurrentHashMap<>();

	private ByteArrayOutputStream baos;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ByteArrayInputStream bais;
	private InetAddress ServerAddress;

	/**
	 * Constructor sets up the port number of the server.
	 * 
	 * @param serverPort
	 *            port to which the server is going to connect to.
	 */
	public Server(int serverPort) {
		this.SERVER_PORT = serverPort;
	}

	/**
	 * Start the server by setting up the sockets we need to connect to.
	 */
	public void run() {
		try {
			serverSocket = new DatagramSocket(SERVER_PORT);
		} catch (BindException e) {
			e.printStackTrace();
			return;
		} catch (SocketException e) {
			e.printStackTrace();
		}

		try {
			ServerAddress = InetAddress.getLocalHost();
			SERVER_IP = ServerAddress.toString();
			listening = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}

		// The thread allows for the server to continue its processes while it handles
		// incoming info.
		listenThread = new Thread(new Runnable() {
			public void run() {
				listen();
			}
		});
		MainGame.host = true;
		listenThread.start();
	}

	/**
	 * Generic thread to send information from the server.
	 * 
	 * @param data
	 *            The byte[] of data that we wish to send.
	 * @param address
	 *            The I.P. address that we wish to send to.
	 * @param port
	 *            The port that we wish to send to.
	 * @return nothing
	 */
	public void send(final byte[] data, InetAddress address, int port) {
		sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					if (listening) {
						serverSocket.send(packet);
						sleep(15);
					}
				} catch (IOException e) {
				} catch (InterruptedException e) {
				}
			}
		});
		sendThread.start();
	}

	/**
	 * Ends the game by starting a new thread, sending the game end packet and
	 * closing the server
	 */
	private void gameOver() {
		GameOver gameOver = new GameOver(this);
		gameOver.start();
		try {
			sleep(1000);
			close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Thread to send information about weapons to a client
	 */
	public void weapons() {
		weaponThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Iterator<PickupPoint> it = MainGame.ammoPickupPoints.iterator(); it.hasNext();) {
					PickupPoint p = it.next();
					if (!p.update()) {
						MainGame.displayAllGuns(p);
					}
				}
			}
		});
		weaponThread.start();
	}

	/**
	 * Method to handle information as it is sent from clients.
	 */
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
	/**
	 * This method assigns an ID to the object that has been received from a client.
	 * 
	 * @param bs
	 *            object to be edited.
	 * @param ID
	 *            id of the client.
	 * @return byte[] of the object to be sent.
	 */
	private byte[] editObj(byte[] bs, String ID) {
		try {
			bais = new ByteArrayInputStream(bs);
			in = new ObjectInputStream(bais);
			ClientSender data = (ClientSender) in.readObject();
			data.setID(ID);

			if (!data.alive) {
				if (clients.get(ID).getDeaths() >= 4) {
					gameOver();
				} else {
					clients.get(ID).incDeaths();
				}
			}
			try {
				baos = new ByteArrayOutputStream();
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
				bais.close();
				in.close();
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

	/**
	 * Send data to all clients.
	 * 
	 * @param data
	 *            data to be sent.
	 */
	protected void sendToAll(byte[] data) {
		for (HashMap.Entry<String, ServerClient> client : clients.entrySet()) {
			send(data, client.getValue().getAddress(), client.getValue().getPort());
		}
	}

	/**
	 * Method that sends data to only the relevant clients.
	 * 
	 * @param data
	 *            data to be sent.
	 * @param port
	 *            port to be ignored.
	 * @param address
	 *            address to be ignored.
	 */
	private void sendToRelevant(byte[] data, int port, InetAddress address) {
		for (HashMap.Entry<String, ServerClient> client : clients.entrySet()) {
			if (port == client.getValue().getPort() && (address.equals(client.getValue().getAddress()))) {
				continue;
			}
			send(data, client.getValue().getAddress(), client.getValue().getPort());
		}
	}

	/**
	 * Method to which a ClientSender object is received, edited and sent to all
	 * other clients.
	 * 
	 * @param packet
	 *            ClientSender object.
	 */
	private void broadcastPlayers(DatagramPacket packet) {
		int port = packet.getPort();
		InetAddress address = packet.getAddress();
		ServerClient associatedID;
		byte[] playerAttributes;

		for (HashMap.Entry<String, ServerClient> client : clients.entrySet()) {

			// If only one player exists in the server
			// we store their current location
			if (port == client.getValue().getPort() && address.equals(client.getValue().getAddress())) {
				associatedID = client.getValue();
				playerAttributes = editObj(packet.getData(), associatedID.getID());

				// Setup its ID and get ready to send to other players
				if (!lastKnownPos.containsKey(client.getValue().getID())) {
					lastKnownPos.put(client.getValue().getID(), playerAttributes);
				}
				sendToRelevant(playerAttributes, port, address);
				break;
			}
		}
	}

	/**
	 * Method to handle information sent from the clients
	 * 
	 * @param packet
	 *            packet of data sent by the client.
	 */

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		String action = new String(data).trim();

		if (action.startsWith("/C/")) {
			String id = UUID.randomUUID().toString().trim();
			clients.put(id, (new ServerClient(action.substring(3), packet.getAddress(), packet.getPort(), id)));
			send(("/c/Connected/" + id + "/").getBytes(), address, port);
			// No need to send last known position to only one player.
			if (clients.size() < 2) {
				return;
			}
			for (HashMap.Entry<String, byte[]> client : lastKnownPos.entrySet()) {
				send(client.getValue(), address, port);
			}
			weapons();
			return;
		}

		else if (action.startsWith("/d/")) {
			disconnect(port, address);
			return;
		} else if (action.startsWith("/s/")) {
			sendToRelevant(packet.getData(), port, address);
			return;
		}

		// If we don't have enough players we just update the position of who is
		// currently connected.
		if (clients.size() < 2) {
			for (HashMap.Entry<String, ServerClient> c : clients.entrySet()) {
				if (packet.getPort() == c.getValue().getPort() && c.getValue().address.equals(packet.getAddress())) {
					updatePos(packet.getData(), c.getKey());
					return;
				}
			}
			return;
		}

		else {
			// Game starts
			broadcastPlayers(packet);
		}
	}

	// Could be used by UI to show user what to enter.
	/**
	 * Method to get the server IP.
	 * 
	 * @return the string of the server IP.
	 */
	public String getServerIP() {
		if (SERVER_IP.contains("/")) {
			if (SERVER_IP.split("/")[1] != null) {
				return SERVER_IP.split("/")[1];
			} else {
				return SERVER_IP;
			}
		} else {
			return SERVER_IP;
		}
	}

	// Update the players position
	/**
	 * Method to update the last known position of a player.
	 * 
	 * @param data
	 *            The clientSender object containing the players data.
	 * @param ID
	 *            ID of that player
	 */
	private void updatePos(byte[] data, String ID) {
		if (!lastKnownPos.containsKey(ID)) {
			lastKnownPos.put(ID, data);
		} else {
			editObj(data, ID);
		}
	}

	/**
	 * Method to see if the server is running.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return listening;
	}

	// Close the server.
	/**
	 * Method to close all threads associated with the server and close its
	 * connection.
	 */
	private void close() {
		try {
			listening = false;
			listenThread.interrupt();
			serverSocket.setSoTimeout(1000);
			serverSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to disconnect a specific player from the game as they send a
	 * disconnection packet to the server. If they are the host, everyone else will
	 * get disconnected.
	 * 
	 * @param port
	 *            port of the player.
	 * @param address
	 *            IP address of the player.
	 */
	private void disconnect(int port, InetAddress address) {
		// Find who specifically disconnected
		for (HashMap.Entry<String, ServerClient> client : clients.entrySet()) {
			if (port == client.getValue().getPort() && address.equals(client.getValue().getAddress())) {
				String[] disconnectIParray = client.getValue().getAddress().toString().split("/");
				String disconnectIP = disconnectIParray[1];

				if (client.getValue().getAddress().equals(ServerAddress) || disconnectIP.equals("localhost")
						|| disconnectIP.equals("127.0.0.1")) {
					sendToAll("/b/".getBytes());

					close();
					return;
				}
				// Otherwise we tell everyone who disconnected and remove them.

				String idToRemove = client.getValue().getID();
				String disconnectIT = "/d/" + idToRemove + "/";

				lastKnownPos.remove(client.getValue().getID());
				clients.remove(client.getKey(), client.getValue());
				System.out.println(client.getKey() + " " + client.getValue().getAddress());
				byte[] disconnected = disconnectIT.getBytes();
				sendToRelevant(disconnected, port, address);
			}
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
