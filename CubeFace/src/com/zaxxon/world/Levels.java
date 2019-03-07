package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import java.awt.geom.Point2D;
import java.util.*;

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
	public static final int[][] LEVEL2 = { { 2, 3, 3, 3, 1, 2, 1, 1, 3, 1, 2, 3, 2 },
			{ 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 0, 0, 2, 1, 2, 3, 3, 2 }, { 2, 3, 0, 3, 2, 3, 3, 2, 0, 4, 0, 0, 4 },
			{ 4, 0, 0, 0, 4, 0, 0, 4, 0, 1, 1, 0, 4 }, { 4, 0, 0, 0, 4, 0, 0, 5, 0, 0, 0, 0, 4 },
			{ 4, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 2, 1 }, { 4, 0, 0, 0, 0, 0, 2, 1, 1, 3, 3, 1, 0 },
			{ 2, 0, 0, 3, 2, 0, 4, 0, 0, 0, 0, 0, 0 }, { 4, 0, 0, 0, 1, 3, 1, 3, 3, 2, 0, 0, 0 },
			{ 4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 }, { 1, 3, 2, 1, 0, 1, 0, 2, 1, 1, 0, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 } };

	public static final Point2D.Double[] L1_WAYPOINTS = { new Point2D.Double(512.0, 447.7),
			new Point2D.Double(512.0, 798.4), new Point2D.Double(703.0, 959.4), new Point2D.Double(703.0, 1536.3),
			new Point2D.Double(768.3, 1727.69), new Point2D.Double(768.3, 2048.3), new Point2D.Double(447.7, 2239.6),
			new Point2D.Double(768.3, 2239.6), new Point2D.Double(1215.69, 2304.3), new Point2D.Double(1536.3, 2304.3),
			new Point2D.Double(2048.5, 1951.27), new Point2D.Double(2048.5, 1471.69),
			new Point2D.Double(1727.69, 1471.69), new Point2D.Double(2239.7, 1792.3),
			new Point2D.Double(2239.7, 1471.69), new Point2D.Double(2048.3, 1024.3),
			new Point2D.Double(2048.3, 703.69) };

	public static Wall[] generateBackground(int[][] level, int size) {
		Wall[] allWalls = new Wall[level.length * level[0].length];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				Wall w = null;
				if (level[i][j] != 0) {
					w = newWall(level[i][j] - 1, size, j * size, i * size);
				}
				allWalls[i * level[0].length + j] = w;
			}
		}
		return allWalls;
	}

	private static Wall newWall(int wallSprite, int size, int x, int y) {
		Wall w = new Wall(size, size, x, y, wallSprite);
		return w;
	}

	public static void generateLevel(int[][] level, int size) {
		Wall[] bg = generateBackground(level, size);
		for (Wall w : bg) {
			if (w != null) {
				MainGame.addSpriteToBackground(w);
				MainGame.addCollidable(w.getHitBox());
			}
		}
	}

}
