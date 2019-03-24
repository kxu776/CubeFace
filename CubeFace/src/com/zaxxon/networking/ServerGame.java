
package com.zaxxon.networking;

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




import com.zaxxon.world.Camera;
import com.zaxxon.world.CollidableRectangle;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.Tile;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;
import com.zaxxon.world.mobile.enemies.Hunter;
import com.zaxxon.world.mobile.enemies.Zombie;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javafx.util.Pair;

/**
 * The main window for the game and all its processes
 * 
 * @author Philip Eagles
 *
 */
/**
 * @author Philip Eagles
 *
 */
public class ServerGame {

	private  Group grpGame;
	private  Group world;
	private  Group background;
	private  Group foreground;
	private  Group overlay;
	private  Group collidables;
	ConcurrentLinkedQueue<Sprite> spriteList;
	public  ArrayList<Player> playerList;
	public  ArrayList<Enemy> enemiesList;
	public  Client networkingClient;
	private  Scene renderedScene;
	public  ClientSender client;
	public  boolean multiplayer = false;
	private  boolean spawn = false;
	 boolean fired = false;

	private  Player player1;
	public  ConcurrentHashMap<String, Player> play = new ConcurrentHashMap<>();

	private  long fpsLong;
	private  double normalisedFPS;
	private  long gameStartTime;

	public  LinkedBlockingQueue<ClientSender> inputUpdateQueue = new LinkedBlockingQueue<ClientSender>();
	public  LinkedBlockingQueue<String> enemyUpdateQueue = new LinkedBlockingQueue<String>();




	private  void spawnRandomEnemy() {
		double randomPercentage = Math.random();
		Tile randomTile;
		boolean escapeFlag = true;
		do {
			randomTile = Tile.getAllTiles().get(ThreadLocalRandom.current().nextInt(0, Tile.getAllTiles().size()));
			int baseTileX = (int) ((randomTile.getX() - randomTile.getWidth() / 2) / randomTile.getWidth());
			int baseTileY = (int) ((randomTile.getY() - randomTile.getHeight() / 2) / randomTile.getHeight());
			int sum = 0;
			for (int i = 0; i < 4; i++) {
				sum += Levels.LEVEL2[baseTileY + (i / 2) % 2][baseTileX + i % 2];
			}
			if (sum == 0) {
				escapeFlag = false;
			}
		} while (escapeFlag);
		if (randomPercentage < 0.7) {
			Zombie enemy = new Zombie(randomTile.getX(), randomTile.getY());
			enemiesList.add(enemy);
			addSpriteToForeground(enemy);
		} else {
			Hunter enemy = new Hunter(randomTile.getX(), randomTile.getY());
			enemiesList.add(enemy);
			addSpriteToForeground(enemy);
		}
	}

	/**
	 * sets the focus on the game scene for control input
	 */
	public  void setGameFocus() {
		grpGame.requestFocus();
	}

	private  void calculateFPS() {
		double smoothingFactor = 0.05;
		normalisedFPS = 1.0 / ((1000.0 / (System.currentTimeMillis() - fpsLong) / 60) * smoothingFactor
				+ ((1.0 / normalisedFPS) * (1.0 - smoothingFactor)));
		fpsLong = System.currentTimeMillis();
	}

	public  long getGameStartTime() {
		return gameStartTime;
	}

	/**
	 * Getter for the renderedScene Scene
	 * 
	 * @return the renderedScene
	 */
	public  Scene getRenderedScene() {
		return renderedScene;
	}

	/**
	 * Getter for the world Group
	 * 
	 * @return the world
	 */
	public  Group getWorld() {
		return world;
	}

	private  void dealWithKeyInput() {
	}

	public  void removeFromGame(Object o) {
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

	private  void removeFromGroup(Group g, Object o) {
		Platform.runLater(() -> {
			g.getChildren().remove(o);
		});
	}

	/**
	 * adds a Sprite to the background of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToBackground(Sprite s) {
		background.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a Sprite to the foreground of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToForeground(Sprite s) {
		Platform.runLater(() -> {
			foreground.getChildren().add(s);
			spriteList.add(s);
			if (s.getClass() == Player.class) {
				playerList.add((Player) s);
			}
		});
	}

	/**
	 * adds a Sprite to the overlay of the game
	 * 
	 * @param s the Sprite to be added
	 */
	public  void addSpriteToOverlay(Sprite s) {
		overlay.getChildren().add(s);
		spriteList.add(s);
	}

	/**
	 * adds a CollidableRectangle to the collidable of the game
	 * 
	 * @param c the CollidableRectangle to be added
	 */
	public  void addCollidable(CollidableRectangle c) {
		collidables.getChildren().add(c);
	}

	private  void sendNetworkUpdate() {
		client.currWep = player1.getCurrentWeaponNum();
		
		if (spawn == false) {
			client.setX(player1.getX());
			client.setY(player1.getY());
			client.setHealth(player1.getHealth());
			networkingClient.sendPlayerObj(client);
			spawn = true;
			return;
		}
		if (!Input.isKeyPressed(KeyCode.SPACE)) {
			fired = false;
		}

		if (player1.getdir() == (FacingDir.up)) {
			client.pos = 1;
		} else if (player1.getdir() == (FacingDir.down)) {
			client.pos = 2;
		} else if (player1.getdir() == (FacingDir.left)) {
			client.pos = 3;
		} else if (player1.getdir() == (FacingDir.right)) {
			client.pos = 4;
		}
		
		// Standing still and shooting
		if (Input.isKeyPressed(KeyCode.SPACE) &&
			((player1.getX() - client.getX()) == 0.0) &&
			((player1.getY() - client.getY()) == 0.0)) {
			if (fired == false) {
				fired = true;
				client.setX(player1.getX());
				client.setY(player1.getY());
				client.setHealth(player1.getHealth());
				client.shoot = true;
				networkingClient.sendPlayerObj(client);
				client.shoot = false;
				return;
			}
		}
		// Standing still
		if (((player1.getX() - client.getX()) == 0.0) && ((player1.getY() - client.getY()) == 0.0)) {
			return;
		}

		if (Input.isKeyPressed(KeyCode.SPACE)) {
			if (fired == false) {
				fired = true;
				client.shoot = true;
			}
		}
			// Moving
			client.setX(player1.getX());
			client.setY(player1.getY());
			client.setHealth(player1.getHealth());
			networkingClient.sendPlayerObj(client);
			client.shoot = false;
	}

	private  void getPlayerUpdatesFromQueue() {

		while (!inputUpdateQueue.isEmpty()) {
			ClientSender data = inputUpdateQueue.poll();
			String id = data.getID().trim();
			newPlayer(data);

			Iterator<Sprite> iterator = spriteList.iterator();
			while (iterator.hasNext()) {
				Sprite sprite = iterator.next();
				if (sprite.getId().equals(id)) {
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
						Vector2 wepPos = play.get(id).weaponManager.getWeaponPos(pos, play.get(id).getplayerDimensions(), vect);
						play.get(id).weaponManager.getCurrentWeapon().fire(vect, wepPos, true);
					}
				}
			}
		}
	}

	public  void updateEnemies() {
		LinkedList<Enemy> killList = new LinkedList<>();
		// Iterates through enemies, updates pos relative to player
		boolean updatedPlayerPos = false;
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

	public  void removeAllMp() {
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
	public  ConcurrentLinkedQueue<Sprite> getSpriteList() {
		return spriteList;
	}
	
	private  void getAiUpdatesFromQueue() {
		boolean exists = false;
		while (!enemyUpdateQueue.isEmpty()) {
			String enemy = enemyUpdateQueue.poll();
			String messageArr[] = enemy.split("/");
			double x = 0.00;
			double y = 0.00;
			try {
				 x = Double.parseDouble(messageArr[0]);
				 y = Double.parseDouble(messageArr[1]);
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
			}			
			for(Enemy sprite: enemiesList) {
				if(sprite.getId().equals(messageArr[2])){
					if(sprite.isAlive() && spriteList.contains(sprite)) {
						sprite.setX(x);
						sprite.setY(y);
						exists = true;
						break;
					}
					else {
						break;
					}
				}
			}
			
			if(exists == true) {
				continue;
			}
			else {
				System.out.println("creating new zombie4");
				Enemy enemymp  = new Zombie(x,y);
				enemymp.setId(messageArr[2]);
				addSpriteToForeground(enemymp);
				exists = false;
				continue;
				}
			}
		}

	public  void newPlayer(ClientSender data) {
		String id = data.getID();
		if(!play.containsKey(data.getID())) {
				play.put(id, new Player());
				Player player = play.get(id);
				player.setX(data.getX());
				player.setY(data.getY());
				player.setId(id);
				player.mp = true;
				player.weaponManager.getCurrentWeapon().test = true;
				player.weaponManager.mp = true;
				addSpriteToForeground(player);
		}
	}
	
	/**
	 * returns a Sprite based off its unique ID
	 * 
	 * @param id the ID of the Sprite
	 * @return the Sprite if it exists else null
	 */
	public  Sprite getSprite(String id) {
		for (Sprite sprite : spriteList) {
			if (sprite.getId().equals(id)) {
				return sprite;
			}
		}
		return null;
	}
	public  void addZombie() {
		Zombie zombie = new Zombie(500,500);
		addSpriteToForeground(zombie);
	}
	
	public  void startMP() {
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		enemiesList = new ArrayList<Enemy>();
		fpsLong = System.currentTimeMillis();
		normalisedFPS = 1;
		gameStartTime = System.currentTimeMillis();

		for (int i = 0; i < 5; i++) {
			spawnRandomEnemy();
		}

		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				for (Player player : playerList) {
					player.update(normalisedFPS);
				}
				getPlayerUpdatesFromQueue();
				updateEnemies();
				calculateFPS();
			}
		};
		mainGameLoop.start();
	}

	public void reset() {
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

		
		grpGame.prefWidth(998);
		grpGame.prefHeight(498);

		Wall.resetWalls();
		spriteList = new ConcurrentLinkedQueue<Sprite>();
		playerList = new ArrayList<Player>();
		enemiesList = new ArrayList<Enemy>();

		
		// loads the level
		Levels.generateLevel(Levels.LEVEL2);
	}



}
