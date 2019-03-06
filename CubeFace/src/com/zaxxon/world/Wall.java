package com.zaxxon.world;

import java.util.ArrayList;

import com.zaxxon.gameart.SpriteImages;

import javafx.geometry.Bounds;
import javafx.util.Pair;

/**
 * @author philip
 *
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
	
	public Wall(double width, double height, double x, double y, int wallType) {
		initialise(width, height, x, y, wallType);
	}
	
	public static void resetWalls() {
		allWalls = new ArrayList<Wall>();
	}
	
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
				hitBox = new CollidableRectangle(width, height * 217.0/256.0, x, (y + 5.0/128.0 * height));
				break;
			case 3:
				hitBox = new CollidableRectangle(0.5 * width, height, x + 1.0/4.0 * width, y);
				break;
			case 4:
				hitBox = new CollidableRectangle(0.5 * width, height, x + 1.0/4.0 * width, y);
				break;
		}
		allWalls.add(this);
	}
	
	public int getWallType() {
		return wallType;
	}
	
	public CollidableRectangle getHitBox() {
		return hitBox;
	}
	
	public static ArrayList<Wall> getAllWalls(){
		return allWalls;
	}
	
	
	/**
	 * Returns an ArrayList of type Bounds so that collisions can be detected.
	 * <p>
	 * The ArrayList is generated from all Walls
	 * 
	 * @return 		the ArrayList<Bounds> of all Walls
	 */
	public static ArrayList<Bounds> getAllWallBounds(){
		ArrayList<Bounds> allBounds = new ArrayList<Bounds>();
		for (Wall w : allWalls) {
			allBounds.add(w.getHitBox().getBoundsInParent());
		}
		return allBounds;
	}
	
	/**
	 * Returns an ArrayList of all Wall types and their bounding box so that collisions can be detected.
	 * 
	 * @return 		the ArrayList<Pair<Integer, Bounds>>of all Walls
	 */
	public static ArrayList<Pair<Integer, Bounds>> getAllWallBoundsWithType(){
		ArrayList<Pair<Integer, Bounds>> allBoundPairs = new ArrayList<Pair<Integer, Bounds>>();
		ArrayList<Wall> allWalls = getAllWalls();
		for (int i = 0; i < allWalls.size(); i++) {
			allBoundPairs.add(new Pair<Integer, Bounds>(allWalls.get(i).getWallType(), allWalls.get(i).getHitBox().getBoundsInParent()));
		}
		return allBoundPairs;
	}

	/* (non-Javadoc)
	 * 
	 * Walls are considered alive so that they are not removed from the game, despite having no health
	 * 
	 * @see com.zaxxon.world.Sprite#isAlive()
	 */
	@Override
	public Boolean isAlive() {
		return true;
	}

}
