package com.zaxxon.world.mobile.enemies;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.WallCollision;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.zaxxon.world.Levels.L2_WAYPOINTS;
import static java.lang.Math.abs;

public abstract class Enemy extends MovableSprite {

	FacingDir facingDir;

	protected static final int TARGET_WIDTH = 64;
	protected static final int TARGET_HEIGHT = 64;

	protected BufferedImage[] sprites;
	protected ImagePattern[] imgPats;

	protected final String DEFAULT_SPRITESHEET = SpriteImages.ZOMBIE_SPRITESHEET_URL;

	protected static double pX, pY;
	protected double prevX, prevY;
	protected boolean pathfinding;

	public int killReward;

	double deltaTime;

	Vector2 inputDir = new Vector2();
	Vector2 moveDir = new Vector2();
	Vector2 velocity = new Vector2();
	final double maxSpeed = 1.5;
	final double acceleration = 1.2;
	final double deceleration = -0.6;
	double currentSpeed = 0;
	private double damage = 0.1;
	final double pfOffset = 0.9;  //1.0

	/**
	 * Default Class constructor - spawns enemy at point (0,0)
	 */
	public Enemy() {
		controllable = false;
		setX(0);
		setY(0);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		MainGame.enemiesList.add(this);
	}

	/**
	 * Class constructor specifying spawn location of enemy
	 *
	 * @param spawnX x-coordinate of spawn location
	 * @param spawnY y-coordinate of spawn location
	 */
	public Enemy(double spawnX, double spawnY) {
		controllable = false;
		this.setX(spawnX);
		this.setY(spawnY);
		setImageSpriteSheet(SpriteImages.ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
		setImageFromSpriteSheet(0);
		this.setWidth(TARGET_WIDTH);
		this.setHeight(TARGET_HEIGHT);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		MainGame.enemiesList.add(this);
	}

	/**
	 * Class constructor specifying spawn location and texture file-path of enemy
	 *
	 * @param spawnX	x-coordinate of spawn location
	 * @param spawnY	Y-coordinate of spawn location
	 * @param spritesheet	filepath to texture spritesheet.
	 */
	public Enemy(double spawnX, double spawnY, BufferedImage spritesheet) {
		controllable = false;
		setX(spawnX);
		setY(spawnY);
		setImageSpriteSheet(spritesheet, 2, 4);
		setImageFromSpriteSheet(0);
		setWidth(TARGET_WIDTH);
		setHeight(TARGET_HEIGHT);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		MainGame.enemiesList.add(this);
	}

	public static double getTargetWidth() {
		return TARGET_WIDTH;
	}

	public static double getTargetHeight() {
		return TARGET_HEIGHT;
	}

	/**
	 * 	Updates all movement, ai and damage functions at each game loop
	 *
	 * @param time number of seconds which have elapsed since last update call
	 * @param player closest player object to enemy
	 */
	public void update(double time, Player player) {
		Point2D.Double closestNode = closestPoint();
		pX = player.getX();
		pY = player.getY();
		damage(player);
		// collision();
		deltaTime = time;
		// movement(pX, pY);
		pathfinding = !lineOfSight();
		if (pathfinding) {
			movement(closestNode.x, closestNode.y);
		} else {
			movement(pX, pY);
		}
		Vector2 toMove = new Vector2(velocity.x * deltaTime, velocity.y * deltaTime);
		this.translate(toMove);
		collision();
		rotate(pX, pY);
		if (this.getX() > (closestNode.getX() - pfOffset) && this.getX() < (closestNode.getX() + pfOffset)
				&& this.getY() > (closestNode.getY() - pfOffset) && this.getY() < (closestNode.getY() + pfOffset)) {
			pathfinding = false;
		} else if (this.getX() > (prevX - pfOffset) && this.getY() < (prevX + pfOffset)
				&& this.getY() > (prevY - pfOffset) && this.getY() < (prevY + pfOffset)) {
			pathfinding = true;
		}
		prevX = this.getX();
		prevY = this.getY();
		// System.out.println("\nx:" + String.valueOf(this.getX()) + " y:" +
		// String.valueOf(this.getY()));
		// System.out.print("\nPathfinding: " + String.valueOf(pathfinding));

	}

	/**
	 * Calculates and executes movement of enemy towards a set of target coordinates
	 *
	 * @param pX target x-coordinate
	 * @param pY target y-coordinate
	 */
	protected void movement(double pX, double pY) {

		inputDir = new Vector2();
		// ordering swaps to handle diagonal facing directions in the correct order
		if (abs(pX - this.getX()) > abs(pY - this.getY())) {
			moveY(pY);
			moveX(pX);
		} else {
			moveX(pX);
			moveY(pY);

		}
		inputDir = Vector2.normalise(inputDir);

		if (Vector2.getMagnitude(inputDir) > 0) {
			currentSpeed = Math.min(maxSpeed, currentSpeed + acceleration * deltaTime);
			moveDir = inputDir;
		} else {
			currentSpeed = Math.max(0, currentSpeed + deceleration * deltaTime);
		}
		velocity = new Vector2(moveDir.x * currentSpeed, moveDir.y * currentSpeed);
	}

	/**
	 * Calculates the cardinal direction of the target point relative to the enemy in the horizontal plain
	 * @param pX target x-coordinate
	 */
	protected void moveX(double pX) {
		if (this.getX() < pX) { // enemy is to the left of the player
			inputDir.x = 1;
		} else if (this.getX() > pX) { // enemy is to the right of the player
			inputDir.x = -1;
		} else
			inputDir.x = 0; // enemy is horizontally inline with the player.
	}

	/**
	 *	Calculates the cardinal direction of the target point relative to the enemy in the vertical plain
	 *
	 * @param pY target x-coordinate
	 */
	protected void moveY(double pY) {
		if (this.getY() < pY) { // enemy is above the player
			inputDir.y = 1;
		} else if (this.getY() > pY) { // enemy is below the player
			inputDir.y = -1;
		} else
			inputDir.y = 0; // enemy is vertically inline with the player.
	}

	/**
	 *	Calculates the cardinal direction of target coordinates in relation to this enemy. This corresponding texture of this will be set, based upon which way it should be facing.
	 *
	 * @param pX target x-coordinate
	 * @param pY target y-coordinate
	 */
	protected void rotate(double pX, double pY) {
		double deltaX = getX() - pX;
		double deltaY = getY() - pY;
		int roughDir = (int) Math.round(Math.atan2(deltaY, deltaX) / Math.PI * 4);
		roughDir = (roughDir + 8 + 6) % 8;
		setImageFromSpriteSheet(roughDir);
		switch (roughDir) {
		case 0:
			this.facingDir = FacingDir.up;
			return;
		case 1:
			this.facingDir = FacingDir.upRight;
			return;
		case 2:
			this.facingDir = FacingDir.right;
			return;
		case 3:
			this.facingDir = FacingDir.downRight;
			return;
		case 4:
			this.facingDir = FacingDir.down;
			return;
		case 5:
			this.facingDir = FacingDir.downLeft;
			return;
		case 6:
			this.facingDir = FacingDir.left;
			return;
		case 7:
			this.facingDir = FacingDir.upLeft;
			return;
		}
	}

	/**
	 * Initiates collision between enemy and walls
	 */
	protected void collision() {
		Vector2 toMove = WallCollision.doCollision(this.getBoundsInLocal(), velocity);
		this.translate(toMove);
	}

	/**
	 * Inflicts damage to closest player if in proximity to this enemy
	 * .
	 * @param player player object
	 */
	protected void damage(Player player) {
		if (this.getBoundsInLocal().intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) { // collision
																														// check
			player.takeDamage(this.damage);
			// System.out.println("Health: " + String.valueOf(player.getHealth()));
		}
	}

	/**
	 * Returns the closest path-finding node to the enemy.
	 *
	 * @return Point2D.Double containing coordinates of closest path-finding node
	 */
	public Point2D.Double closestPoint() {
		Map<Point2D.Double, Double> dists = new HashMap<>();
		Point2D.Double currentPoint = new Point2D.Double(this.getX(), this.getY());
		for (int i = 0; i < L2_WAYPOINTS.length; i++) {
			dists.put(L2_WAYPOINTS[i], currentPoint.distanceSq(L2_WAYPOINTS[i]));
		}
		Map.Entry<Point2D.Double, Double> closest = Collections.min(dists.entrySet(),
				Comparator.comparing(Map.Entry::getValue));
		return closest.getKey();
	}

	/**
	 * Checks for line-of-sight obstructions between an enemy and the closest player
	 *
	 * @return true if an unobstructed line-of-sight exists between enemy and player
	 */
	public boolean lineOfSight() {
		boolean lineOfSight = false;
		ArrayList<Bounds> wallBounds = Wall.getAllWallBounds();
		Line line = new Line(this.getX(), this.getY(), pX, pY);
		for (Bounds bounds : wallBounds) {
			if (line.intersects(bounds)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *	Returns a collection of all attribute vales for network transmission
	 *
	 * @return Hashmap of attributes and their titles
	 */
	@Override
	public LinkedHashMap<String, Object> getAttributes() {
		LinkedHashMap<String, Object> attributes = super.getAttributes();
		attributes.put("Damage", damage);
		return attributes;
	}

}