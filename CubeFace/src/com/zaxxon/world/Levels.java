package com.zaxxon.world;

import com.zaxxon.client.MainGame;

public class Levels {

	public static final int[][] LEVEL0 = { { 2, 1, 3, 1, 1, 1, 3, 2 }, { 4, 0, 0, 0, 0, 0, 0, 4 },
			{ 2, 3, 3, 2, 3, 3, 0, 4 }, { 4, 0, 0, 4, 0, 0, 0, 4 }, { 4, 0, 1, 1, 3, 2, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 0, 0, 0, 0, 4 }, { 2, 1, 1, 1, 1, 1, 1, 2 } };
	public static final int[][] LEVEL1 = { { 2, 1, 1, 1, 3, 2, 3, 3, 1, 1, 2 }, { 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4 },
			{ 2, 3, 0, 0, 0, 4, 0, 0, 0, 0, 4 }, { 4, 0, 0, 0, 0, 2, 1, 3, 0, 0, 4 },
			{ 4, 0, 0, 2, 3, 2, 0, 0, 0, 0, 4 }, { 4, 0, 0, 5, 0, 4, 0, 0, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 4, 0, 4, 0, 1, 2 }, { 2, 3, 1, 0, 0, 2, 1, 1, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 0, 0, 0, 4 }, { 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 1 } };

	public static Wall[] generateBackground(int[][] level, int size) {
		Wall[] allWalls = new Wall[level.length * level[0].length];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level.length; j++) {
				Wall w = null;
				if (level[i][j] != 0) {
					w = newWall(level[i][j] - 1, size);
				}
				allWalls[i * level.length + j] = w;
			}
		}
		return allWalls;
	}

	private static Wall newWall(int wallSprite, int size) {
		Wall w = new Wall(wallSprite);
		w.setWidth(size);
		w.setHeight(size);
		return w;
	}

	public static void generateLevel(int[][] level, int size) {
		Wall[] bg = generateBackground(level, size);
		for (int i = 0; i < bg.length; i++) {
			Wall s = bg[i];
			if (s != null) {
				s.setX(size * (i % level.length));
				s.setY(size * (i / level.length));
				MainGame.addSpriteToBackground(s);
			}
		}
	}

}
