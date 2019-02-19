package com.zaxxon.world;

import java.util.ArrayList;
import java.util.LinkedList;

import com.zaxxon.gameart.SpriteImages;

import javafx.geometry.Bounds;
import javafx.util.Pair;

public class Wall extends Sprite {

	public static final int WALL_CORNER = 0;
	public static final int WALL_CORNER_WITH_VERTICAL = 1;
	public static final int WALL_HORIZONTAL = 2;
	public static final int WALL_VERTICAL = 3;
	public static final int WALL_VERTICAL_END = 4;
	
	private int wallType;
	

	public Wall() {
		initialise(0);
	}
	
	public Wall(int wallType) {
		initialise(wallType);
	}
	
	private void initialise(int wallType) {
		this.wallType = wallType;
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
	}
	
	public int getWallType() {
		return wallType;
	}
	
	public static ArrayList<Wall> getAllWalls(LinkedList<Sprite> allSprites){
		ArrayList<Wall> allWalls = new ArrayList<Wall>();
		for(Sprite s : allSprites) {
			if(s.getClass() == Wall.class) {
				allWalls.add((Wall) s);
			}
		}
		return allWalls;
	}
	
	
	/**
	 * Returns an ArrayList of type Bounds so that collisions can be detected.
	 * <p>
	 * The ArrayList is generated from all Walls in the scene
	 * 
	 * @param allSprites	LinkedList<Sprite> of all Sprites to iterate over
	 * @return 				the ArrayList<Bounds> of all Walls
	 */
	public static ArrayList<Bounds> getAllWallBounds(LinkedList<Sprite> allSprites){
		ArrayList<Bounds> allBounds = new ArrayList<Bounds>();
		ArrayList<Wall> allWalls = getAllWalls(allSprites);
		for (Wall w : allWalls) {
			allBounds.add(w.getBoundsInParent());
		}
		return allBounds;
	}
	public static ArrayList<Bounds> getAllWallBounds(ArrayList<Wall> allWalls){
		ArrayList<Bounds> allBounds = new ArrayList<Bounds>();
		for (Wall w : allWalls) {
			allBounds.add(w.getBoundsInParent());
		}
		return allBounds;
	}
	
	public static ArrayList<Pair<Integer, Bounds>> getAllWallBoundsWithType(LinkedList<Sprite> allSprites){
		ArrayList<Pair<Integer, Bounds>> allBoundPairs = new ArrayList<Pair<Integer, Bounds>>();
		ArrayList<Wall> allWalls = getAllWalls(allSprites);
		ArrayList<Bounds> allBounds = getAllWallBounds(allWalls);
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
