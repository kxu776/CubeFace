package com.zaxxon.client;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.ClientSender;

import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.Toolbox;
import com.zaxxon.sound.MusicPlayer;

import com.zaxxon.world.TrackingCamera;
import com.zaxxon.ui.tools.StatsBox;
import com.zaxxon.world.CollidableRectangle;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Tile;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Brute;
import com.zaxxon.world.mobile.enemies.Enemy;
import com.zaxxon.world.mobile.enemies.Hunter;
import com.zaxxon.world.mobile.enemies.Zombie;
import com.zaxxon.world.pickups.PickupPoint;
import com.zaxxon.world.shooting.AmmoPickup;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The main window for the game and all its processes
 */
public class MainGame {

	private static Group grpGame;
	private static Group world;
	private static Group background;
	private static Group foreground;
	private static Group overlay;
	private static Group collidables;
	private static TrackingCamera camera;
	private static ConcurrentLinkedQueue<Sprite> spriteList;
	public static ArrayList<Player> playerList;
	public static ArrayList<Enemy> enemiesList;
	public static ArrayList<AmmoPickup> ammoPickupList;
	public static ArrayList<PickupPoint> ammoPickupPoints;
	private static Client networkingClient;
	private static Scene renderedScene;
	private static ClientSender client;
	public static boolean host = false;
	public static boolean multiplayer = false;
	private static boolean spawn = false;
	private static boolean fired = false;
	public static boolean muted = false;
	private static AnimationTimer mainGameLoop;

	private static MusicPlayer music;

	private static Player player1;
	public static ConcurrentHashMap<String, Player> play = new ConcurrentHashMap<>();
	private static AnchorPane anchorPane;

	private static long fpsLong;
	private static double normalisedFPS;
	private static long gameStartTime;
	private static long nextEnemySpawnTime;
	private static long nextPickupSpawnTime;

	public static LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();
	public static LinkedBlockingQueue<String> weaponQueue = new LinkedBlockingQueue<String>();

	/**
	 * resets all the game's objects (including UI and world)
	 * 
	 * @param primaryStage
	 *            the Stage to render the game in
	 * @param m
	 *            the MusicPlayer for music
	 */
	public static void reset(Stage primaryStage) {
		try {
			Tile.reset();
			Wall.reset();
		} catch (Exception e) {
			Tile.clear();
			Wall.resetWalls();
		}

		// set up game groups
		grpGame = new Group();
		grpGame.setId("grpGame");
		world = new Group();
		world.setId("world");
		collidables = new Group();
		collidables.setId("collidables");
		background = new Group();
		background.setId("background");
		foreground = new Group();
		foreground.setId("foreground");
		overlay = new Group();
		overlay.setId("overlay");
		world.getChildren().add(background);
		world.getChildren().add(foreground);
		world.getChildren().add(collidables);
		grpGame.getChildren().add(world);
		grpGame.getChildren().add(overlay);
		double[] xOffset = { 0 }; // array for making window movable
		double[] yOffset = { 0 };

		// make a rectangle
		Rectangle gameRect = new Rectangle(MainMenu.WIDTH - 2, MainMenu.HEIGHT - 2);
		gameRect.setLayoutX(1);
		gameRect.setLayoutY(1);

		grpGame.prefWidth(MainMenu.WIDTH);
		grpGame.prefHeight(MainMenu.HEIGHT);
		// clip the group
		grpGame.setClip(gameRect);

		BorderPane borderPane;
		// make a statsbox
		if (multiplayer) {
			borderPane = StatsBox.statsBox(2);
		} else {
			borderPane = StatsBox.statsBox(1);
		}

		// make a toolbox
		AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 3, "CubeFace");
		toolbox.setPrefWidth(MainMenu.WIDTH - 2);
		toolbox.setId("toolbox");

		// make an audio button
		Button audio = new Button();
		Image audioIcon = new Image(MainMenu.class.getResource("img/audio.png").toString());
		ImageView audioView = new ImageView(audioIcon); // make an imageview for the minimise icon
		audio.setGraphic(audioView); // add the image to the button

		audio.setOnAction(e -> {
			muted = (muted) ? false : true;
			if (muted) {
				music.stop();
			} else {
				music.play();
			}
			MainGame.setGameFocus();
		});
		audio.setStyle("-fx-background-color: none; -fx-border: none; -fx-padding: 27 0 0 5;");

		// make an anchor pane to hold the game and the stats box
		anchorPane = new AnchorPane();
		anchorPane.setTopAnchor(toolbox, 0.0);
		anchorPane.setLeftAnchor(toolbox, 0.0);
		anchorPane.setBottomAnchor(borderPane, 0.0);
		anchorPane.setRightAnchor(borderPane, 0.0);
		anchorPane.setLeftAnchor(audio, 0.0);
		anchorPane.setCenterShape(true);
		anchorPane.getChildren().addAll(grpGame, borderPane, toolbox, audio);
		anchorPane.setId("anchorpane");

		// set up new arrays and objects
		Wall.resetWalls();
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		enemiesList = new ArrayList<Enemy>();
		ammoPickupList = new ArrayList<AmmoPickup>();
		ammoPickupPoints = new ArrayList<>();
		player1 = new Player();
		player1.setX(500);
		player1.setY(500);
		addSpriteToForeground(player1);

		// make a rectangle
		Rectangle rect = new Rectangle(MainMenu.WIDTH, MainMenu.HEIGHT);
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		anchorPane.setClip(rect);

		// sets up the scene
		renderedScene = new Scene(anchorPane, MainMenu.WIDTH, MainMenu.HEIGHT);
		renderedScene.setFill(Color.TRANSPARENT);
		renderedScene.getStylesheets().add(MainMenu.class.getResource("css/maingame.css").toString());

		// make it movable
		renderedScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset[0] = event.getSceneX();
				yOffset[0] = event.getSceneY();
			}
		});

		renderedScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset[0]);
				primaryStage.setY(event.getScreenY() - yOffset[0]);
			}
		});
	}

	/**
	 * sets the music track to the MusicPlayer
	 * 
	 * @param m
	 */
	public static void setMusic(MusicPlayer m) {
		music = m;
	}

	/**
	 * begins the main game loop
	 *
	 * @param primaryStage
	 *            the Stage to render on
	 */
	public static void start(Stage primaryStage) {
		// loads the level
		if (multiplayer) {
			Levels.generateLevel(Levels.MP_LEVEL);
		} else {
			Levels.generateLevel(Levels.LEVEL2);
		}
		// sets up the game camera
		camera = new TrackingCamera(player1);
		primaryStage.setScene(renderedScene);
		grpGame.setFocusTraversable(true);
		setGameFocus();

		Input.addHandlers(primaryStage);

		if (!muted) {
			music.play();
		}
		if (multiplayer && host) {
			for (Point2D.Double pickupPointcoords : Levels.MP_AMMO_SPAWNS) { // Creates MP ammo spawnpoints on map
				ammoPickupPoints.add(new PickupPoint(pickupPointcoords));
				spawnAmmoPickup(ammoPickupPoints.get(ammoPickupPoints.size() - 1)); // Spawns pickup at newly created
																					// spawn point
			}
		}

		fpsLong = System.currentTimeMillis();
		normalisedFPS = 1;
		gameStartTime = System.currentTimeMillis();

		if (!multiplayer) {
			for (int i = 0; i < 5; i++) {
				spawnRandomEnemy();
			}
		}
		spawnRandomAmmoPickup();

		mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				for (Player player : playerList) {
					player.update(normalisedFPS, primaryStage);
				}
				updatePickups();
				if (multiplayer) {
					sendNetworkUpdate();
					getPlayerUpdatesFromQueue();
					weaponSpawnQueue();
				} else {
					spawnZombieCheck();
					spawnPickupCheck();
					updateEnemies();
				}
				camera.update();
				calculateFPS();
			}
		};
		mainGameLoop.start();
	}

	/**
	 * ends the game loop
	 */
	public static void stop() {
		mainGameLoop.stop();
		camera.setSpriteToFollow(null);
	}

	/**
	 * checks whether or not to spawn a new zombie, and if necessary spawns it
	 */
	private static void spawnZombieCheck() {
		if (System.currentTimeMillis() >= nextEnemySpawnTime) {
			spawnRandomEnemy();
		}
	}

	/**
	 * checks whether or not to spawn a new pickup, and if necessary spawns it
	 */
	private static void spawnPickupCheck() {
		if (System.currentTimeMillis() >= nextPickupSpawnTime) {
			spawnRandomAmmoPickup();
		}
	}

	/**
	 * spawns a random enemy in the world
	 */
	private static void spawnRandomEnemy() {
		double randomPercentage = Math.random();
		Tile randomTile = getRandomFreeTile(2);
		if (randomPercentage < 0.6) {
			Zombie enemy = new Zombie(randomTile.getX(), randomTile.getY());
			addSpriteToForeground(enemy);
		} else if (randomPercentage < 0.9) {
			Hunter enemy = new Hunter(randomTile.getX(), randomTile.getY());
			addSpriteToForeground(enemy);
		} else {
			Brute enemy = new Brute(randomTile.getX(), randomTile.getY());
			addSpriteToForeground(enemy);
		}
		nextEnemySpawnTime = System.currentTimeMillis();
		nextEnemySpawnTime += 1500 + ThreadLocalRandom.current().nextInt(2000, 20000)
				/ ((nextEnemySpawnTime - gameStartTime) / 40000.0 + 1);
	}

	private static Tile getRandomFreeTile(int minDistance) {
		Tile randomTile;
		do {
			randomTile = Tile.getAllTiles().get(ThreadLocalRandom.current().nextInt(0, Tile.getAllTiles().size()));
			int baseTileX = (int) ((randomTile.getX() - randomTile.getWidth() / 2) / randomTile.getWidth());
			int baseTileY = (int) ((randomTile.getY() - randomTile.getHeight() / 2) / randomTile.getHeight());
			// checks the tile isn't a wall
			if (Levels.LEVEL2[baseTileY][baseTileX] != 0) {
				continue;
			}
			if (minDistance > 0) {
				int playerTileX = (int) Math.floor(player1.getX() / Levels.SIZE);
				int playerTileY = (int) Math.floor(player1.getY() / Levels.SIZE);
				// ensures object is at least 3 tiles away from the player so doesn't spawn too
				// close
				if (Math.abs(baseTileX - playerTileX) + Math.abs(baseTileY - playerTileY) > minDistance) {
					break;
				}
			} else {
				break;
			}
		} while (true);
		return randomTile;
	}

	/**
	 * spawns a random pickup in the world
	 */
	private static void spawnRandomAmmoPickup() {
		if (!multiplayer) {
			Tile randomTile = getRandomFreeTile(2);
			int pickupType = ThreadLocalRandom.current().nextInt(0, 3);
			AmmoPickup a = new AmmoPickup(pickupType, new Vector2(randomTile.getX(), randomTile.getY()), null);
			ammoPickupList.add(a);
			addSpriteToForeground(a);
			nextPickupSpawnTime = System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(10000, 30000);
		}
	}

	/**
	 * Spawns a randomised ammo pickup at the designated spawnpoint object passed to
	 * it.
	 *
	 * @param pickupPoint
	 *            Multiplayer spawn point object for item pickups
	 */
	public static void spawnAmmoPickup(PickupPoint pickupPoint) {
		AmmoPickup ammoPickup = new AmmoPickup(ThreadLocalRandom.current().nextInt(0, 1 + 1),
				pickupPoint.getPosVector(), pickupPoint); // spawn random ammo pickup at location
		if (host) {
			String s = "/s/" + ammoPickup.type + "/" + pickupPoint.getPosVector().x + "/" + pickupPoint.getPosVector().y
					+ "/";
			networkingClient.send(s.getBytes());
		}
		if (!ammoPickupList.contains(ammoPickup)) {
			ammoPickupList.add(ammoPickup);
			addSpriteToForeground(ammoPickup);
		}
	}

	public static void displayAllGuns(PickupPoint pickupPoint) {
		for (int i = 0; i < ammoPickupList.size(); i++) {
			String s = "/s/" + ammoPickupList.get(i).type + "/" + pickupPoint.getPosVector().x + "/"
					+ pickupPoint.getPosVector().y + "/";
			networkingClient.send(s.getBytes());
		}
	}

	/**
	 * sets the focus on the game scene for control input
	 */
	public static void setGameFocus() {
		grpGame.requestFocus();
	}

	/**
	 * calculates a normalised FPS for updates with delta time
	 */
	private static void calculateFPS() {
		double smoothingFactor = 0.05;
		normalisedFPS = 1.0 / ((1000.0 / (System.currentTimeMillis() - fpsLong) / 60) * smoothingFactor
				+ ((1.0 / normalisedFPS) * (1.0 - smoothingFactor)));
		fpsLong = System.currentTimeMillis();
	}

	/**
	 * @return the start time of the game in milliseconds
	 */
	public static long getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Getter for the renderedScene Scene
	 * 
	 * @return the renderedScene
	 */
	public static Scene getRenderedScene() {
		return renderedScene;
	}

	/**
	 * Getter for the world Group
	 * 
	 * @return the world
	 */
	public static Group getWorld() {
		return world;
	}

	/**
	 * removes an object from the game, including List references
	 * 
	 * @param o
	 *            the object to remove
	 */
	public static void removeFromGame(Object o) {
		if (o instanceof Rectangle) {
			Sprite s = (Sprite) o;
			removeFromGroup((Group) s.getParent(), o);
			if (spriteList.contains(s)) {
				spriteList.remove(s);
				if (o instanceof Player) {
					Player p = (Player) o;
					playerList.remove(p);
				} else if (o instanceof Enemy) {
					Enemy e = (Enemy) o;
					enemiesList.remove(e);
				}
			}
		} else if (o instanceof CollidableRectangle) {
			CollidableRectangle cr = (CollidableRectangle) o;
			removeFromGroup((Group) cr.getParent(), o);
		}
	}

	/**
	 * removes the object from the group
	 * 
	 * @param g
	 *            the group containing the object
	 * @param o
	 *            the object to remove
	 */
	private static void removeFromGroup(Group g, Object o) {
		Platform.runLater(() -> {
			g.getChildren().remove(o);
		});
	}

	/**
	 * adds a Sprite to the background of the game
	 * 
	 * @param s
	 *            the Sprite to be added
	 */
	public static void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a Sprite to the foreground of the game
	 * 
	 * @param s
	 *            the Sprite to be added
	 */
	public static void addSpriteToForeground(Sprite s) {
		foreground.getChildren().add(s);
		spriteList.add(s);
		if (s.getClass() == Player.class) {
			playerList.add((Player) s);
		}
	}

	/**
	 * adds a Sprite to the overlay of the game
	 * 
	 * @param s
	 *            the Sprite to be added
	 */
	public static void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a CollidableRectangle to the collidable of the game
	 * 
	 * @param c
	 *            the CollidableRectangle to be added
	 */
	public static void addCollidable(CollidableRectangle c) {
		collidables.getChildren().add(c);
	}

	private static void sendNetworkUpdate() {
		// Send info about where the player has died and where they are due to spawn.
		if (!player1.isAlive()) {
			Tile randomTile = getRandomFreeTile(0);
			player1.setX(randomTile.getX());
			player1.setY(randomTile.getY());
			player1.reset();
			client.pos = 2;
			client.alive = false;
			client.setX(player1.getX());
			client.setY(player1.getY());
			networkingClient.sendPlayerObj(client);
		}
		client.alive = true;
		// Manage if the player has fired or not
		if (!Input.isKeyPressed(KeyCode.SPACE)) {
			fired = false;
		}
		client.currWep = player1.getCurrentWeaponNum();
		client.pos = player1.getDir();
		double x = player1.getX();
		double y = player1.getY();

		if (spawn == false) {
			client.setX(x);
			client.setY(y);
			client.setHealth(player1.getHealth());
			networkingClient.sendPlayerObj(client);
			spawn = true;
			return;
		}

		// Standing still and shooting
		if (Input.isKeyPressed(KeyCode.SPACE) && ((x - client.getX()) == 0.0) && ((y - client.getY()) == 0.0)) {
			if (fired == false) {
				if (player1.getCurrentWeaponNum() != 1) {
					fired = true;
				}
				client.setX(x);
				client.setY(y);
				client.setHealth(player1.getHealth());
				client.shoot = true;
				networkingClient.sendPlayerObj(client);
				client.shoot = false;
				return;
			}
		}
		// Standing still
		else if (((player1.getX() - client.getX()) == 0.0) && ((player1.getY() - client.getY()) == 0.0)) {
			return;
		}

		else if (Input.isKeyPressed(KeyCode.SPACE)) {
			if (fired == false) {
				if (player1.getCurrentWeaponNum() != 1) {
					fired = true;
				}
				client.shoot = true;
			}
		}
		// Moving or moving and shooting
		client.setX(x);
		client.setY(y);
		client.setHealth(player1.getHealth());
		networkingClient.sendPlayerObj(client);
		client.shoot = false;
	}

	/**
	 * Method to Update the multiplayer sprites within the main game loop.
	 */
	private static void getPlayerUpdatesFromQueue() {
		while (!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			// Player does not exist
			if (data.getID() == null) {
				return;
			}
			String id = data.getID().trim();
			// create a new player if they dont already exist.
			newPlayer(data);
			
			// Iterate through the sprites to find the one associated with the ID.
			for (Iterator<Sprite> iterator = spriteList.iterator(); iterator.hasNext();) {
				Sprite sprite = iterator.next();

				// Update the sprite if they died.
				if (sprite.getId().equals(id)) {
					if (!data.alive) {
						player1.updateScore();
						player1.displayStats();
						play.get(id).reset();
						sprite.setX(data.getX());
						sprite.setY(data.getY());
						((Player) sprite).setDir(data.pos);
						continue;
					}
					// Otherwise update their location, health, direction and weapon .
					sprite.setX(data.getX());
					sprite.setY(data.getY());
					((Player) sprite).setDir(data.pos);
					if (sprite instanceof MovableSprite) {
						((MovableSprite) sprite).setHealth(data.getHealth());
					}
					if ((play.get(id).weaponManager.getCurrentWeaponNum() != data.currWep)) {
						play.get(id).weaponManager.setCurrentWeapon(data.getCurrWep());
						play.get(id).weaponManager.getCurrentWeapon().test = true;
					}
					if ((data.shoot == true)) {
						FacingDir m = play.get(id).getdir();
						Vector2 vect = play.get(id).weaponManager.getFacingDirAsVector(m);
						Vector2 pos = play.get(id).weaponManager.playerPos;
						Vector2 wepPos = play.get(id).weaponManager.getWeaponPos(pos,
								         play.get(id).getplayerDimensions(), vect);
						play.get(id).weaponManager.getCurrentWeapon().fire(vect, wepPos, true);
					}
				}
			}
		}
	}

	/**
	 * Updates each ai controlled enemy in game - handles movement, damage and
	 * pathfinding for each enemy. Removes dead enemies from the game.
	 */
	private static void updateEnemies() {
		LinkedList<Enemy> killList = new LinkedList<>();
		// Iterates through enemies, updates pos relative to player
		for (Enemy sprite : enemiesList) {
			if (!sprite.isAlive()) {
				killList.add(sprite); // Cannot kill sprite during iteration
			} else {
				Pair<Double, Player> closestPlayer = null;
				for (Player player : playerList) {
					if (closestPlayer == null) {
						closestPlayer = new Pair<Double, Player>(sprite.getDistanceToSprite(player), player);
					} else if (sprite.getDistanceToSprite(player) < closestPlayer.getKey()) {
						closestPlayer = new Pair<Double, Player>(sprite.getDistanceToSprite(player), player);
					}
				}
				((Enemy) sprite).update(normalisedFPS, closestPlayer.getValue());
			}
		}
		for (Enemy sprite : killList) {
			sprite.lastHitReceived.score += sprite.killReward;
			spriteList.remove(sprite);
			enemiesList.remove(sprite);
			sprite.delete();
		}
	}

	/**
	 * Updates item pick-up spawn points in multiplayer mode. Checks for
	 * spawns/despawns on each designated point, and implements a respawn cooldown
	 * for pickup items.
	 */
	private static void updatePickups() {

		/*
		 * for (int i = 0; i < ammoPickupList.size(); i++) {
		 * 
		 * ammoPickupList.get(i).update(); }
		 */
		if (multiplayer) {
			for (PickupPoint pickupPoint : ammoPickupPoints) {
				if (pickupPoint.update()) { // If an item is due to spawn at this point, spawn the item.
					spawnAmmoPickup(pickupPoint);
				}
			}
		} else {
			for (AmmoPickup aP : ammoPickupList) {
				aP.update();
			}
		}
	}

	/**
	 * Method to clear the game of all multiplayer players.
	 */
	public static void removeAllMp() {
		for (ConcurrentHashMap.Entry<String, Player> players : play.entrySet()) {
			getSprite(players.getKey()).delete();
			removeFromGame(getSprite(players.getKey()));
		}
		play.clear();
	}

	/**
	 * Getter for the spriteList
	 * 
	 * @return the spriteList
	 */
	public static ConcurrentLinkedQueue<Sprite> getSpriteList() {
		return spriteList;
	}

	private static void weaponSpawnQueue() {
		while (!weaponQueue.isEmpty()) {
			String weapon = weaponQueue.poll();
			String[] attributes = weapon.split("/");
			int type = 0;
			double x = 0.00;
			double y = 0.00;
			try {
				type = Integer.parseInt(attributes[0]);
				x = Double.parseDouble(attributes[1]);
				y = Double.parseDouble(attributes[2]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			Point2D.Double point = new Point2D.Double(x, y);
			PickupPoint pointtopic = new PickupPoint(point);
			AmmoPickup ammoPickup = new AmmoPickup((type), (new Vector2(x, y)), pointtopic); // spawn random ammo pickup
																								// at location
			ammoPickupList.add(ammoPickup);
			addSpriteToForeground(ammoPickup);
		}
	}
	
	/**
	 * Method to add a new player sprite to the game for multiplayer.
	 * @param data Contains a clientsender object that has info about the player.
	 */
	private static void newPlayer(ClientSender data) {
		String id = data.getID();
		if (!play.containsKey(data.getID())) {
			play.put(id, new Player());
			Player player = play.get(id);
			player.setX(data.getX());
			player.setY(data.getY());
			player.setId(id);
			player.setDir(data.pos);
			player.mp = true;
			player.weaponManager.getCurrentWeapon().test = true;
			player.weaponManager.mp = true;
			addSpriteToForeground(player);
		}
	}

	/**
	 * returns a Sprite based off its unique ID
	 * 
	 * @param id
	 *            the ID of the Sprite
	 * @return the Sprite if it exists else null
	 */
	public static Sprite getSprite(String id) {
		for (Sprite sprite : spriteList) {
			if (sprite.getId().equals(id)) {
				return sprite;
			}
		}
		return null;
	}

	/**
	 * getter for the Player object
	 * 
	 * @return gets the Player
	 */
	public static Player getPlayer() {
		return player1;
	}
	
	/**
	 * Sets up the Client thread for multiplayer.
	 * @param host ip of the server
	 * @param port port of the server
	 * @param name name of the player
	 */
	public static void setUpClientThread(String host, int port, String name) {
		client = new ClientSender(player1.getX(), player1.getY(), player1.getHealth());
		networkingClient = new Client(host, port, name);
		networkingClient.start();
	}
	
	/**
	 * Method for creating a point 2D double.
	 * @param x
	 * @param y
	 * @return a point 2D double.
	 */
	public static Point2D.Double pick(double x, double y) {
		return new Point2D.Double(x, y);
	}

	/**
	 * Getter for the networkingClient thread.
	 * @return networkingClient 
	 */
	public static Client getNetworkingClient() {
		return networkingClient;
	}

}
