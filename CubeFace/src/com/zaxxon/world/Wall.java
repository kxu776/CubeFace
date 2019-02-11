package com.zaxxon.world;

import com.zaxxon.gameart.SpriteImages;

public class Wall extends Sprite {
	
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
		this.setImageSpriteSheet(SpriteImages.WALL_SPRITESHEET_IMAGE, 1, 5);
		this.setImageFromSpriteSheet(wallType);
	}

}
