package com.zaxxon.world;

import java.util.ArrayList;

import com.zaxxon.gameart.SpriteImages;

import javafx.geometry.Bounds;
import javafx.util.Pair;

public class Wall extends Sprite {

	public static final int WALL_CORNER = 0;
	public static final int WALL_CORNER_WITH_VERTICAL = 1;
	public static final int WALL_HORIZONTAL = 2;
	public static final int WALL_VERTICAL = 3;
	public static final int WALL_VERTICAL_END = 4;
	
	private static ArrayList<Wall> allWalls;
	
	private int wallType;

	public Wall() {
		initialise(0);
	}
	
	public Wall(int wallType) {
		initialise(wallType);
	}
	
	public static void resetWalls() {
		allWalls = new ArrayList<Wall>();
	}
	
	private void initialise(int wallType) {
		this.wallType = wallType;
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
		allWalls.add(this);
	}
	
	public int getWallType() {
		return wallType;
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
			allBounds.add(w.getBoundsInParent());
		}
		return allBounds;
	}
	
	public static ArrayList<Pair<Integer, Bounds>> getAllWallBoundsWithType(){
		ArrayList<Pair<Integer, Bounds>> allBoundPairs = new ArrayList<Pair<Integer, Bounds>>();
		ArrayList<Wall> allWalls = getAllWalls();
		ArrayList<Bounds> allBounds = getAllWallBounds();
		for (int i = 0; i < allWalls.size(); i++) {
			allBoundPairs.add(new Pair<Integer, Bounds>(allWalls.get(i).getWallType(), allBounds.get(i)));
		}
		return allBoundPairs;
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

}
