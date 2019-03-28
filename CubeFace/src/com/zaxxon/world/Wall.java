package com.zaxxon.world;

import java.util.ArrayList;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

import javafx.geometry.Bounds;
import javafx.util.Pair;

/**
 * a Wall Sprite used in the game
 */
public class Wall extends Sprite {

	public static final int WALL_CORNER = 0;
	public static final int WALL_CORNER_WITH_VERTICAL = 1;
	public static final int WALL_HORIZONTAL = 2;
	public static final int WALL_VERTICAL = 3;
	public static final int WALL_VERTICAL_END = 4;

	private static ArrayList<Wall> allWalls;

	private int wallType;
	private CollidableRectangle hitBox;

	/**
	 * constructor for a new Wall
	 * 
	 * @param width    the width of the wall in pixels
	 * @param height   the height of the wall in pixels
	 * @param x        the x position of the wall in pixels
	 * @param y        the x position of the wall in pixels
	 * @param wallType the appearance of the wall from the spritesheet
	 */
	public Wall(double width, double height, double x, double y, int wallType) {
		initialise(width, height, x, y, wallType);
	}

	/**
	 * resets the array of Walls
	 */
	public static void resetWalls() {
		allWalls = new ArrayList<Wall>();
	}

	/**
	 * initialises the Wall
	 * 
	 * @param width    the width of the wall in pixels
	 * @param height   the height of the wall in pixels
	 * @param x        the x position of the wall in pixels
	 * @param y        the x position of the wall in pixels
	 * @param wallType the appearance of the wall from the spritesheet
	 */
	private void initialise(double width, double height, double x, double y, int wallType) {
		this.wallType = wallType;
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
		this.setWidth(width);
		this.setHeight(height);
		this.setX(x);
		this.setY(y);
		switch (wallType) {
		case 0:
			hitBox = new CollidableRectangle(width, height, x, y);
			break;
		case 1:
			hitBox = new CollidableRectangle(width, height, x, y);
			break;
		case 2:
			hitBox = new CollidableRectangle(width, height * 217.0 / 256.0, x, (y + 5.0 / 128.0 * height));
			break;
		case 3:
			hitBox = new CollidableRectangle(0.5 * width, height, x + 1.0 / 4.0 * width, y);
			break;
		case 4:
			hitBox = new CollidableRectangle(0.5 * width, height, x + 1.0 / 4.0 * width, y);
			break;
		}
		allWalls.add(this);
	}

	/**
	 * @return getter for the Wall's type
	 */
	public int getWallType() {
		return wallType;
	}

	/**
	 * @return getter for a Wall's hit box
	 */
	public CollidableRectangle getHitBox() {
		return hitBox;
	}

	/**
	 * @return getter for an ArrayList of Walls
	 */
	public static ArrayList<Wall> getAllWalls() {
		return allWalls;
	}

	/**
	 * Returns an ArrayList of type Bounds so that collisions can be detected.
	 * <p>
	 * The ArrayList is generated from all Walls
	 * 
	 * @return the ArrayList<Bounds> of all Walls
	 */
	public static ArrayList<Bounds> getAllWallBounds() {
		ArrayList<Bounds> allBounds = new ArrayList<Bounds>();
		for (Wall w : allWalls) {
			allBounds.add(w.getHitBox().getBoundsInParent());
		}
		return allBounds;
	}

	/**
	 * Returns an ArrayList of all Wall types and their bounding box so that
	 * collisions can be detected.
	 * 
	 * @return the ArrayList<Pair<Integer, Bounds>>of all Walls
	 */
	public static ArrayList<Pair<Integer, Bounds>> getAllWallBoundsWithType() {
		ArrayList<Pair<Integer, Bounds>> allBoundPairs = new ArrayList<Pair<Integer, Bounds>>();
		ArrayList<Wall> allWalls = getAllWalls();
		for (int i = 0; i < allWalls.size(); i++) {
			allBoundPairs.add(new Pair<Integer, Bounds>(allWalls.get(i).getWallType(),
					allWalls.get(i).getHitBox().getBoundsInParent()));
		}
		return allBoundPairs;
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

	@Override
	public void delete() {
		allWalls.remove(this);
		hitBox.delete();
		MainGame.removeFromGame(this);
	}
	
	public static void reset() {
		for(Wall w  : allWalls) {
			w.delete();
		}
	}

}
