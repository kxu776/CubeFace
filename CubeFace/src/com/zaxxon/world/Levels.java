package com.zaxxon.world;

import com.zaxxon.client.MainGame;
import com.zaxxon.world.mobile.enemies.Enemy;

import javafx.util.Pair;

import java.awt.geom.Point2D;
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
			{ 4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 }, { 1, 3, 2, 1, 0, 1, 0, 2, 1, 1, 0, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 0, 0, 4, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 } };
	public static final Point2D.Double[] L2_WAYPOINTS = generateCornerPoints(LEVEL2);
	public static final int[][] MP_LEVEL = { { 2, 3, 3, 3, 3, 3, 2 }, { 4, 0, 0, 0, 0, 0, 4 }, { 4, 0, 1, 0, 1, 0, 4 },
			{ 4, 0, 0, 0, 0, 0, 4 }, { 4, 0, 1, 0, 1, 0, 4 }, { 4, 0, 0, 0, 0, 0, 4 }, { 1, 3, 3, 3, 3, 3, 1 } };

	public static final Point2D.Double[] MP_AMMO_SPAWNS = { new Point2D.Double(880, 864)/* Centre */,
			new Point2D.Double(607, 852)/* CentreLeft */, new Point2D.Double(1128, 852)/* CentreRight */,
			new Point2D.Double(865, 1139)/* CentreTop */, new Point2D.Double(865, 589)/* CentreBottom */ };
	public static final Point2D.Double[] MP_HEALTH_SPAWNS = { new Point2D.Double(323, 1384)/* BottomLeft */,
			new Point2D.Double(1405, 1398)/* BottomRight */, new Point2D.Double(1405, 304)/* TopRight */,
			new Point2D.Double(285, 304)/* TopLeft */ };

	/**
	 * generates and populates the background for the world with the floor Tile
	 * objects and Wall objects, and also populates the collidables with
	 * CollidableRectangle objects from the Wall objects
	 * 
	 * @param level the level to be generated
	 */
	public static void generateLevel(int[][] level) {
		Wall[] bg = generateBackgroundWalls(level);
		Tile[] ts = generateBackgroundTiles(level);
		for (Tile t : ts) {
			if (t != null) {
				MainGame.addSpriteToBackground(t);
			}
		}
		for (Wall w : bg) {
			if (w != null) {
				MainGame.addSpriteToBackground(w);
				MainGame.addCollidable(w.getHitBox());
			}
		}
	}

	/**
	 * generates an array of Walls for a given int[][] level
	 * 
	 * @param level the level to generate the Walls from
	 * @return all Wall objects in an array
	 */
	private static Wall[] generateBackgroundWalls(int[][] level) {
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

	/**
	 * generates an array of Tiles for a given int[][] level
	 * 
	 * @param level the level to generate the Tiles from
	 * @return all Tile objects in an array
	 */
	private static Tile[] generateBackgroundTiles(int[][] level) {
		LinkedList<Tile> allTiles = new LinkedList<Tile>();
		// trims the level down to a polygon
		level = removeRedundantLines(level);
		// get the top leftmost wall
		int startX = 0;
		int startY = 0;
		while (true) {
			if (startX > level[0].length) {
				startY++;
				startX = 0;
			}
			if (level[startY][startX] != 0) {
				break;
			}
			startX++;
		}
		LinkedList<Pair<Integer, Integer>> toFill = new LinkedList<Pair<Integer, Integer>>();
		toFill.add(new Pair<Integer, Integer>(startX, startY));
		while (!toFill.isEmpty()) {
			Pair<Integer, Integer> currentToFill = toFill.poll();
			if (level[currentToFill.getValue()][currentToFill.getKey()] == -1) {
				continue;
			}
			Tile t = new Tile(currentToFill.getKey() * SIZE + SIZE / 2, currentToFill.getValue() * SIZE + SIZE / 2,
					SIZE, SIZE);
			allTiles.add(t);
			int[] adjacents = getAdjacentsExceptionFree(level, currentToFill.getKey(), currentToFill.getValue(), -1);
			LinkedList<Pair<Integer, Integer>> adjacentPositions = getAdjacentPositions(level, currentToFill.getKey(),
					currentToFill.getValue());
			if (level[currentToFill.getValue()][currentToFill.getKey()] == 0) {
				if (adjacents[0] > -1) {
					toFill.add(adjacentPositions.get(0));
				}
				if (adjacents[1] == 0 || (adjacents[1] > -1 && currentToFill.getKey().equals(startX)
						&& currentToFill.getValue().equals(startY))) {
					toFill.add(adjacentPositions.get(1));
				}
				if (adjacents[2] == 0 || (adjacents[2] > -1 && currentToFill.getKey().equals(startX)
						&& currentToFill.getValue().equals(startY))) {
					toFill.add(adjacentPositions.get(2));
				}
				if (adjacents[3] > -1) {
					toFill.add(adjacentPositions.get(3));
				}
			}
			if (level[currentToFill.getValue()][currentToFill.getKey()] > 0) {
				if (adjacents[1] == 0 || (adjacents[1] > -1 && currentToFill.getKey().equals(startX)
						&& currentToFill.getValue().equals(startY))) {
					toFill.add(adjacentPositions.get(1));
				}
				if (adjacents[2] == 0 || (adjacents[1] > -1 && currentToFill.getKey().equals(startX)
						&& currentToFill.getValue().equals(startY))) {
					toFill.add(adjacentPositions.get(2));
				}
			}
			level[currentToFill.getValue()][currentToFill.getKey()] = -1;
		}
		Tile[] allTilesArray = new Tile[0];
		allTilesArray = allTiles.toArray(allTilesArray);
		return allTilesArray;
	}

	/**
	 * generates a new Wall object
	 * 
	 * @param wallType the type of Wall being generated (corner, horizontal etc.)
	 * @param x        the x position of the left of the Wall
	 * @param y        the y position of the top of the Wall
	 * @return the new Wall object
	 */
	private static Wall newWall(int wallType, int x, int y) {
		Wall w = new Wall(SIZE, SIZE, x, y, wallType);
		return w;
	}

	/**
	 * generates the corner points inside the level used by the zombie pathfinding
	 * 
	 * @param level the level to process
	 * @return an array of points corresponding to the corners within the level
	 */
	private static Point2D.Double[] generateCornerPoints(int[][] level) {
		LinkedList<Point2D.Double> knownObtuseCorners = new LinkedList<Point2D.Double>();
		for (int centreY = 0; centreY < level.length; centreY++) {
			for (int centreX = 0; centreX < level[centreY].length; centreX++) {
				if (level[centreY][centreX] != 0) {
					int centre = level[centreY][centreX];
					int[] sides = new int[4];
					boolean[] theseCorners;
					try {
						sides = getAdjacents(level, centreX, centreY);
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

	/**
	 * returns a boolean array of length 4 stating whether corners are free or not
	 * 
	 * @param sides  the sides adjacent to the central point
	 * @param centre the central point
	 * @return true for corner, false for not
	 */
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

	/**
	 * returns whether the diagonals to the central point are occupied or not
	 * 
	 * @param level   the level being loaded in
	 * @param centreX x position of the central point
	 * @param centreY y position of the central point
	 * @return true if occupied, false if not
	 */
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

	/**
	 * performs not (array1[i] implies array2[i]) for all elements of arrays.
	 * <p>
	 * Throws an Exception if array lengths do not match.
	 * 
	 * @param array1 the first array to be the left hand side of the equation
	 * @param array2 the second array to be the right hand side of the equation
	 * @return true if !(a->b), else false
	 * @throws Exception thrown when array lengths do not match
	 */
	private static boolean[] booleanArraysNotImplied(boolean[] array1, boolean[] array2) throws Exception {
		if (array1.length == array2.length) {
			boolean[] c = new boolean[array1.length];
			for (int i = 0; i < array1.length; i++) {
				c[i] = array1[i] && !array2[i];
			}
			return c;
		} else {
			throw new Exception("Array lengths do not match");
		}
	}

	/**
	 * removes points that are not within the bounds of a polygon
	 * 
	 * @param knownObtuseCorners a list of Point2D.Doubles known to be obtuse
	 *                           corners
	 * @param level              the level being processed
	 * @return the trimmed list
	 */
	private static LinkedList<Point2D.Double> trimOutsideBounds(LinkedList<Point2D.Double> knownObtuseCorners,
			int[][] level) {
		level = removeRedundantLines(level);
		LinkedList<Point2D.Double> polygonPoints = LevelArrayToLinkedList(level);
		for (int i = knownObtuseCorners.size() - 1; i >= 0; i--) {
			if (windingAlgorithm(knownObtuseCorners.get(i), polygonPoints) < 4) {
				knownObtuseCorners.remove(i);
			}
		}
		return knownObtuseCorners;
	}

	/**
	 * an efficient way of calculating whether points are within a polygon
	 * 
	 * @param point   the point to test
	 * @param polygon the polygon to test against
	 * @return the sum of the number of quadrants passed through (+1 for clockwise
	 *         pass, -1 for anticlockwise)
	 */
	private static int windingAlgorithm(Point2D.Double point, LinkedList<Point2D.Double> polygon) {
		int prevQuad = -1;
		int currentQuad;
		int sumClockwise = -1;
		for (int i = 0; i < polygon.size() + 1; i++) {
			Point2D.Double polyPoint = polygon.get(i % polygon.size());
			double x = polyPoint.getX() - point.getX();
			double y = polyPoint.getY() - point.getY();
			// 0 mxmy
			// 1 pxmy
			// 3 pxpy
			// 2 mxpy
			if (x < 0) {
				currentQuad = 0;
			} else {
				currentQuad = 1;
			}
			if (y >= 0) {
				currentQuad += 2;
			}
			if (prevQuad != currentQuad) {
				if (currentQuad > prevQuad || (currentQuad == 2 && prevQuad == 3)
						|| (currentQuad == 0 && prevQuad == 2)) {
					sumClockwise++;
				} else {
					sumClockwise--;
				}
				prevQuad = currentQuad;
			}
		}
		return sumClockwise;
	}

	/**
	 * converts a Point from int form to expanded doubles
	 * 
	 * @param point the point to transform
	 * @return normalised point
	 */
	private static Point2D.Double normalisePoint(Point2D.Double point) {
		double newX = point.getX() * SIZE + SIZE / 2;
		double newY = point.getY() * SIZE + SIZE / 2;
		Point2D.Double normalised = new Point2D.Double(newX, newY);
		return normalised;
	}

	/**
	 * removes lines that are not connected at 2 ends by backtracking
	 * 
	 * @param level the level to remove lines from
	 * @return the processed level
	 */
	public static int[][] removeRedundantLines(int[][] level) {
		// removes lines that aren't connected at both ends
		LinkedList<Pair<Integer, Integer>> pointsRemoved = new LinkedList<Pair<Integer, Integer>>();
		int[][] reducedLevel = new int[level.length][];
		for (int i = 0; i < level.length; i++) {
			reducedLevel[i] = level[i].clone();
		}
		for (int centreY = 0; centreY < level.length; centreY++) {
			for (int centreX = 0; centreX < level[centreY].length; centreX++) {
				int adjacentWalls = 0;
				try {
					int[] adjacents = getAdjacents(level, centreX, centreY);
					for (int a : adjacents) {
						if (a > 0) {
							adjacentWalls++;
						}
					}
				} catch (Exception e) {
					adjacentWalls = 4;
				}
				if (adjacentWalls < 2) {
					pointsRemoved.add(new Pair<Integer, Integer>(centreX, centreY));
					reducedLevel[centreY][centreX] = 0;
				}
			}
		}
		while (!pointsRemoved.isEmpty()) {
			Pair<Integer, Integer> removed = pointsRemoved.poll();
			try {
				int[] adjacents = getAdjacents(reducedLevel, removed.getKey(), removed.getValue());
				LinkedList<Pair<Integer, Integer>> adjacentPositions = getAdjacentPositions(reducedLevel,
						removed.getKey(), removed.getValue());
				for (int i = 0; i < adjacents.length; i++) {
					if (adjacents[i] > 0) {
						int centreX = adjacentPositions.get(i).getKey();
						int centreY = adjacentPositions.get(i).getValue();
						int adjacentWalls = 0;
						try {
							int[] adjacentsNew = getAdjacents(reducedLevel, centreX, centreY);
							for (int a : adjacentsNew) {
								if (a > 0) {
									adjacentWalls++;
								}
							}
						} catch (Exception e) {
							adjacentWalls = 4;
						}
						if (adjacentWalls < 2) {
							pointsRemoved.add(new Pair<Integer, Integer>(centreX, centreY));
							reducedLevel[centreY][centreX] = 0;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(":(");
			}
		}
		return reducedLevel;
	}

	/**
	 * gets all wall states adjacent to the central point
	 * 
	 * @param level   the level to process
	 * @param centreX the central x position
	 * @param centreY the central y position
	 * @return an int[] length 4 for the adjacents in order top, right, bottom, left
	 */
	private static int[] getAdjacents(int[][] level, int centreX, int centreY) {
		int[] sides = new int[4];
		// top
		sides[0] = level[centreY - 1][centreX];
		// right
		sides[1] = level[centreY][centreX + 1];
		// bottom
		sides[2] = level[centreY + 1][centreX];
		// left
		sides[3] = level[centreY][centreX - 1];
		return sides;
	}

	/**
	 * gets all Wall states adjacent to the central point without throwing an
	 * exception if no point exists (i.e. no indexing issues)
	 * 
	 * @param level          the level where adjacents are being found
	 * @param centreX        the central x position
	 * @param centreY        the central y position
	 * @param exceptionValue the value to set for when an exception occurs
	 * @return an int[] length 4 for the adjacents in order top, right, bottom, left
	 */
	private static int[] getAdjacentsExceptionFree(int[][] level, int centreX, int centreY, int exceptionValue) {
		int[] sides = new int[4];
		// top
		try {
			sides[0] = level[centreY - 1][centreX];
		} catch (Exception e) {
			sides[0] = exceptionValue;
		}
		// right
		try {
			sides[1] = level[centreY][centreX + 1];
		} catch (Exception e) {
			sides[1] = exceptionValue;
		}
		// bottom
		try {
			sides[2] = level[centreY + 1][centreX];
		} catch (Exception e) {
			sides[2] = exceptionValue;
		}
		// left
		try {
			sides[3] = level[centreY][centreX - 1];
		} catch (Exception e) {
			sides[3] = exceptionValue;
		}
		return sides;
	}

	/**
	 * returns a list of the adjacent positions to a point
	 * 
	 * @param level   the level being processed
	 * @param centreX the central x position
	 * @param centreY the central y position
	 * @return a linked list of the adjacent points
	 */
	private static LinkedList<Pair<Integer, Integer>> getAdjacentPositions(int[][] level, int centreX, int centreY) {
		LinkedList<Pair<Integer, Integer>> sides = new LinkedList<Pair<Integer, Integer>>();
		// top
		sides.add(new Pair<Integer, Integer>(centreX, centreY - 1));
		// right
		sides.add(new Pair<Integer, Integer>(centreX + 1, centreY));
		// bottom
		sides.add(new Pair<Integer, Integer>(centreX, centreY + 1));
		// left
		sides.add(new Pair<Integer, Integer>(centreX - 1, centreY));
		return sides;
	}

	/**
	 * converts a 2D array to a LinkedList
	 * 
	 * @param levelArray the level to be processed
	 * @return a list of points
	 */
	private static LinkedList<Point2D.Double> LevelArrayToLinkedList(int[][] levelArray) {
		// create linkedlist for values
		LinkedList<Point2D.Double> linkedList = new LinkedList<Point2D.Double>();
		// get top-leftmost point for start
		int startX = 0;
		int startY = 0;
		while (levelArray[startY][startX] == 0) {
			startX++;
			if (startX > levelArray[startY].length) {
				startY++;
				startX = 0;
			}
		}
		// initialise variables
		int nextX = startX;
		int nextY = startY;
		int currentDir = 1;
		int prevDir = 0;
		do {
			// check point is a corner
			// helps reduce number of points
			if (prevDir != currentDir) {
				prevDir = currentDir;
				// insert point
				linkedList.add(normalisePoint(new Point2D.Double(nextX, nextY)));
			}
			// check for initial values to prevent overwrite and allow loop finish
			if (nextX != startX || nextY != startY) {
				// remove from array to prevent duplication
				levelArray[nextY][nextX] = 0;
			}
			// get next 4 potential points
			int[] upcomingAdjacents = getAdjacentsExceptionFree(levelArray, nextX, nextY, 0);
			// biased look towards previous direction
			for (int attemptDir = 0; attemptDir < 4; attemptDir++) {
				// point being observed is a wall
				if (upcomingAdjacents[(attemptDir + currentDir) % 4] != 0) {
					currentDir = (attemptDir + currentDir) % 4;
					break;
				}
			}
			// get the positions corresponding to the direction
			Pair<Integer, Integer> nextTarget = getAdjacentPositions(levelArray, nextX, nextY).get(currentDir);
			// update the position
			nextX = nextTarget.getKey();
			nextY = nextTarget.getValue();
		} while (nextX != startX || nextY != startY);
		return linkedList;
	}

}
