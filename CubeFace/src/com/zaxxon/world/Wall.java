package com.zaxxon.world;

import java.util.ArrayList;

import com.zaxxon.gameart.SpriteImages;

import javafx.geometry.Bounds;

public class Wall extends Sprite {
	
	private static ArrayList<Wall> allWalls = new ArrayList<Wall>();
	
	public static final int WALL_CORNER = 0;
	public static final int WALL_CORNER_WITH_VERTICAL = 1;
	public static final int WALL_HORIZONTAL = 2;
	public static final int WALL_VERTICAL = 3;
	public static final int WALL_VERTICAL_END = 4;
	
	public Wall() {
		initialise(0);
	}
	
	public Wall(int wallType) {
		initialise(wallType);
	}
	
	private void initialise(int wallType) {
		addNewWall(this);
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
	}
	
	private static void addNewWall(Wall w) {
		allWalls.add(w);
	}
	
	public static ArrayList<Wall> getAllWalls(){
		return allWalls;
	}
	
	public static ArrayList<Bounds> getAllWallBounds(){
		ArrayList<Bounds> allBounds = new ArrayList<Bounds>();
		ArrayList<Wall> allWalls = getAllWalls();
		for (Wall w : allWalls) {
			allBounds.add(w.getBoundsInParent());
		}
		return allBounds;
	}

}
