package com.zaxxon.client;

import java.io.File;
import java.io.IOException;

import com.zaxxon.world.Sprite;

public class SampleLevel {
	private static final String WALL_SPRITE_SHEET_URL = "CubeFace/src/com/zaxxon/gameart/wall-sprite-sheet.png";
	public static final int SIZE = 64;
	public static final int[][] STATE_SHEET = { { 2, 1, 3, 1, 1, 1, 3, 2 }, { 4, 0, 0, 0, 0, 0, 0, 4 },
			{ 2, 3, 3, 2, 3, 3, 0, 4 }, { 4, 0, 0, 4, 0, 0, 0, 4 }, { 4, 0, 1, 1, 3, 2, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 0, 0, 0, 0, 4 }, { 2, 1, 1, 1, 1, 1, 1, 2 } };
	
	public static Sprite[] generateBackground(){
		Sprite[] allSprites = new Sprite[STATE_SHEET.length * STATE_SHEET[0].length];
		File f = new File(WALL_SPRITE_SHEET_URL);
		for(int i = 0; i < STATE_SHEET.length; i++) {
			for(int j = 0; j < STATE_SHEET.length; j++) {
				Sprite s = null;
				if(STATE_SHEET[i][j] != 0) {
					s = newSprite();
					s.setImageFromSpriteSheet(STATE_SHEET[i][j] - 1);
				}
				allSprites[i*STATE_SHEET.length + j] = s;
			}
		}
		return allSprites;
	}

	public static Sprite newSprite() {
		Sprite s = new Sprite();
		try {
			s.setImageSpriteSheet(WALL_SPRITE_SHEET_URL, 1, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.setWidth(SIZE);
		s.setHeight(SIZE);
		return s;
	}
}
