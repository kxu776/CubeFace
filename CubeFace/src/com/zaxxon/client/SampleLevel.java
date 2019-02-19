package com.zaxxon.client;

import com.zaxxon.world.Sprite;
import com.zaxxon.world.Wall;

public class SampleLevel {
	public static final int SIZE = 128;
	public static final int[][] STATE_SHEET = { { 2, 1, 3, 1, 1, 1, 3, 2 }, { 4, 0, 0, 0, 0, 0, 0, 4 },
			{ 2, 3, 3, 2, 3, 3, 0, 4 }, { 4, 0, 0, 4, 0, 0, 0, 4 }, { 4, 0, 1, 1, 3, 2, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 0, 0, 0, 0, 4 }, { 2, 1, 1, 1, 1, 1, 1, 2 } };

	public static Sprite[] generateBackground() {
		Wall[] allWalls = new Wall[STATE_SHEET.length * STATE_SHEET[0].length];
		for (int i = 0; i < STATE_SHEET.length; i++) {
			for (int j = 0; j < STATE_SHEET.length; j++) {
				Wall w = null;
				if (STATE_SHEET[i][j] != 0) {
					w = newWall(STATE_SHEET[i][j] - 1);
				}
				allWalls[i * STATE_SHEET.length + j] = w;
			}
		}
		return allWalls;
	}

	private static Wall newWall(int wallSprite) {
		Wall w = new Wall(wallSprite);
		w.setWidth(SIZE);
		w.setHeight(SIZE);
		return w;
	}

	public static void generateLevel(MainGame gameWindow) {
		Sprite[] bg = SampleLevel.generateBackground();
		for (int i = 0; i < bg.length; i++) {
			Sprite s = bg[i];
			if (s != null) {
				s.setX(SampleLevel.SIZE * (i % SampleLevel.STATE_SHEET.length));
				s.setY(SampleLevel.SIZE * (i / SampleLevel.STATE_SHEET.length));
				gameWindow.addSpriteToBackground(s);
			}
		}

	}

}
