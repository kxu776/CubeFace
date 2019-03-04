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
	
	private static ArrayList<CollidableRectangle> allWallCollidables;
	
	private int wallType;
	private CollidableRectangle hitBox;
	
	public Wall(double width, double height, double x, double y, int wallType) {
		initialise(width, height, x, y, wallType);
	}
	
	public static void resetWalls() {
		allWallCollidables = new ArrayList<CollidableRectangle>();
	}
	
	private void initialise(double width, double height, double x, double y, int wallType) {
		this.wallType = wallType;
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
		switch (wallType) {
			case 0:
				hitBox = new CollidableRectangle(width, height, x, y, wallType);
			case 1:
				hitBox = new CollidableRectangle(width, height, x, y, wallType);
			case 2:
				hitBox = new CollidableRectangle(width, height * 217/256, x, y + 5/128 * height, wallType);
			case 3:
				hitBox = new CollidableRectangle(0.5 * width, height, x + 1/4 * width, y, wallType);
			case 4:
				hitBox = new CollidableRectangle(0.5 * width, height, x + 1/4 * width, y, wallType);
		}
		allWallCollidables.add(hitBox);
	}
	
	public int getWallType() {
		return wallType;
	}
	
	public static ArrayList<CollidableRectangle> getAllWalls(){
		return allWallCollidables;
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
		for (CollidableRectangle c : allWallCollidables) {
			allBounds.add(c.getBoundsInParent());
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
		ArrayList<CollidableRectangle> allWalls = getAllWalls();
		for (int i = 0; i < allWalls.size(); i++) {
			allBoundPairs.add(new Pair<Integer, Bounds>(allWalls.get(i).getWallType(), allWalls.get(i).getBoundsInParent()));
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
