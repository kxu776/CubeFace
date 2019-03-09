package com.zaxxon.world;

import com.zaxxon.client.MainGame;
import com.zaxxon.world.mobile.enemies.Enemy;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.*;

public class Levels {

	public static final int SIZE = 256;
	public static final int[][] LEVEL0 = { { 2, 1, 3, 1, 1, 1, 3, 2 }, { 4, 0, 0, 0, 0, 0, 0, 4 },
			{ 2, 3, 3, 2, 3, 3, 0, 4 }, { 4, 0, 0, 4, 0, 0, 0, 4 }, { 4, 0, 1, 1, 3, 2, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 0, 0, 0, 0, 4 }, { 2, 1, 1, 1, 1, 1, 1, 2 } };
	public static final Point2D.Double[] L0_WAYPOINTS = generateCornerPoints(LEVEL0);
	public static final int[][] LEVEL1 = { { 2, 1, 1, 1, 3, 2, 3, 3, 1, 1, 2 }, { 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4 },
			{ 2, 3, 0, 0, 0, 4, 0, 0, 0, 0, 4 }, { 4, 0, 0, 0, 0, 2, 1, 3, 0, 0, 4 },
			{ 4, 0, 0, 2, 3, 2, 0, 0, 0, 0, 4 }, { 4, 0, 0, 5, 0, 4, 0, 0, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 4, 0, 4, 0, 1, 2 }, { 2, 3, 1, 0, 0, 2, 1, 1, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 5, 0, 0, 0, 0, 4 }, { 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 1 } };
	public static final Point2D.Double[] L1_WAYPOINTS = generateCornerPoints(LEVEL1);
	public static final int[][] LEVEL2 = { { 2, 3, 3, 3, 1, 2, 1, 1, 3, 1, 2, 3, 2 },
			{ 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 5, 0, 4 }, { 4, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 4 },
			{ 4, 0, 0, 0, 0, 0, 0, 2, 1, 2, 3, 3, 2 }, { 2, 3, 0, 3, 2, 3, 3, 2, 0, 4, 0, 0, 4 },
			{ 4, 0, 0, 0, 4, 0, 0, 4, 0, 1, 1, 0, 4 }, { 4, 0, 0, 0, 4, 0, 0, 5, 0, 0, 0, 0, 4 },
			{ 4, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 2, 1 }, { 4, 0, 0, 0, 0, 0, 2, 1, 1, 3, 3, 1, 0 },
			{ 2, 0, 0, 3, 2, 0, 4, 0, 0, 0, 0, 0, 0 }, { 4, 0, 0, 0, 1, 3, 1, 3, 3, 2, 0, 0, 0 },
			{ 4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 },
			{ 1, 3, 2, 1, 0, 1, 0, 2, 1, 1, 0, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 } };
	public static final Point2D.Double[] L2_WAYPOINTS = generateCornerPoints(LEVEL2);

	/*
	 * public static final Point2D.Double[] L1_WAYPOINTS_OLD = { new
	 * Point2D.Double(512.0, 447.7), new Point2D.Double(512.0, 798.4), new
	 * Point2D.Double(703.0, 959.4), new Point2D.Double(703.0, 1536.3), new
	 * Point2D.Double(768.3, 1727.69), new Point2D.Double(768.3, 2048.3), new
	 * Point2D.Double(447.7, 2239.6), new Point2D.Double(768.3, 2239.6), new
	 * Point2D.Double(1215.69, 2304.3), new Point2D.Double(1536.3, 2304.3), new
	 * Point2D.Double(2048.5, 1951.27), new Point2D.Double(2048.5, 1471.69), new
	 * Point2D.Double(1727.69, 1471.69), new Point2D.Double(2239.7, 1792.3), new
	 * Point2D.Double(2239.7, 1471.69), new Point2D.Double(2048.3, 1024.3), new
	 * Point2D.Double(2048.3, 703.69) };
	 */

	public static Wall[] generateBackground(int[][] level) {
		Wall[] allWalls = new Wall[level.length * level[0].length];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				Wall w = null;
				if (level[i][j] != 0) {
					w = newWall(level[i][j] - 1, j * SIZE, i * SIZE);
				}
				allWalls[i * level[0].length + j] = w;
			}
		}
		return allWalls;
	}

	private static Wall newWall(int wallSprite, int x, int y) {
		Wall w = new Wall(SIZE, SIZE, x, y, wallSprite);
		return w;
	}

	public static void generateLevel(int[][] level) {
		Wall[] bg = generateBackground(level);
		for (Wall w : bg) {
			if (w != null) {
				MainGame.addSpriteToBackground(w);
				MainGame.addCollidable(w.getHitBox());
			}
		}
	}

	public static Point2D.Double[] generateCornerPoints(int[][] level) {
		LinkedList<Point2D.Double> knownObtuseCorners = new LinkedList<Point2D.Double>();
		for (int centreY = 0; centreY < level.length; centreY++) {
			for (int centreX = 0; centreX < level[centreY].length; centreX++) {
				if (level[centreY][centreX] != 0) {
					int centre = level[centreY][centreX];
					int[] sides = new int[4];
					boolean[] theseCorners;
					try {
						// top
						sides[0] = level[centreY - 1][centreX];
						// right
						sides[1] = level[centreY][centreX + 1];
						// bottom
						sides[2] = level[centreY + 1][centreX];
						// left
						sides[3] = level[centreY][centreX - 1];
						boolean[] potentialCorners = getCorners(sides, centre);
						boolean[] otherCorners = getDiagonals(level, centreX, centreY);
						theseCorners = booleanArraysNotImplied(potentialCorners, otherCorners);
					} catch (Exception e) {
						theseCorners = new boolean[] { false, false, false, false };
					}
					for (int k = 0; k < theseCorners.length; k++) {
						if (theseCorners[k]) {
							int isCornerPositiveX = (k / 2 + 1) % 2;
							double cornerX = (centreX + isCornerPositiveX) * SIZE
									+ (isCornerPositiveX * 2 - 1) * Enemy.getTargetWidth();
							int isCornerPositiveY = ((k + 1) / 2) % 2;
							double cornerY = (centreY + isCornerPositiveY) * SIZE
									+ (isCornerPositiveY * 2 - 1) * Enemy.getTargetHeight();
							knownObtuseCorners.add(new Point2D.Double(cornerX, cornerY));
						}
					}
				}
			}
		}
		knownObtuseCorners = trimOutsideBounds(knownObtuseCorners, level);
		return knownObtuseCorners.toArray(new Point2D.Double[0]);
	}

	private static boolean[] getCorners(int[] sides, int centre) {
		// sides: 0 top, 1 right, 2 bottom, 3 left
		boolean[] knownCorners = new boolean[4];
		// iterate over each side
		for (int i = 0; i < sides.length; i++) {
			// e.g. top and right when i = 0
			if (sides[i % sides.length] == 0 && sides[(i + 1) % sides.length] == 0) {
				knownCorners[i] = true;
			} else {
				knownCorners[i] = false;
			}
		}
		return knownCorners;
	}

	private static boolean[] getDiagonals(int[][] level, int centreX, int centreY) {
		// tr, br, bl, tl
		boolean[] knownDiagonals = new boolean[4];
		for (int i = 0; i < 4; i++) {
			int isCornerPositiveX = ((i / 2 + 1) % 2) * 2 - 1;
			int isCornerPositiveY = (((i + 1) / 2) % 2) * 2 - 1;
			if (level[centreY + isCornerPositiveY][centreX + isCornerPositiveX] == 0) {
				knownDiagonals[i] = false;
			} else {
				knownDiagonals[i] = true;
			}
		}
		return knownDiagonals;
	}

	private static boolean[] booleanArraysNotImplied(boolean[] array1, boolean[] array2) throws Exception {
		if (array1.length == array2.length) {
			boolean[] c = new boolean[array1.length];
			for (int i = 0; i < array1.length; i++) {
				c[i] = array1[i] && !array2[i];
			}
			return c;
		} else {
			throw new Exception("Array lengths not matching.");
		}
	}

	private static LinkedList<Point2D.Double> trimOutsideBounds(LinkedList<Point2D.Double> knownObtuseCorners,
			int[][] level) {

		return knownObtuseCorners;
	}

}
