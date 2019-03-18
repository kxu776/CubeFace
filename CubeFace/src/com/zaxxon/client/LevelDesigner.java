package com.zaxxon.client;

import java.util.ArrayList;

import com.zaxxon.input.Input;
import com.zaxxon.world.StaticCamera;
import com.zaxxon.world.Wall;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * A tool for developing new levels. Keyboard controls: wasd move camera, qe
 * zoom camera, arrows move wall to place, b change wall texture, space place a
 * wall, enter print level to console
 * 
 * @author Philip Eagles
 *
 */
public class LevelDesigner extends Application {

	/**
	 * Camera that is used to traverse the Scene
	 */
	private static StaticCamera camera;
	/**
	 * integer representation of the Wall type (texture from sprite sheet)
	 */
	private static int wallToSet = 0;
	/**
	 * size of the template Wall
	 */
	private static int width = 256;
	/**
	 * size of the template Wall
	 */
	private static int height = 256;
	/**
	 * position of the template Wall
	 */
	private static int posX = 0;
	/**
	 * position of the template Wall
	 */
	private static int posY = 0;
	/**
	 * preview of the wall object to be placed/inserted
	 */
	private static Wall templateWall;
	/**
	 * Group for all Sprites to be placed into
	 */
	private static Group world;

	/**
	 * runs the level designer tool
	 * 
	 * @param args command line input options (unused)
	 */
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Level Designer");
		primaryStage.show();
		primaryStage.setWidth(600);
		primaryStage.setHeight(600);

		Thread.sleep(50);

		// set up game group
		Group grpGame = new Group();
		grpGame.setId("grpGame");
		world = new Group();
		world.setId("world");
		grpGame.getChildren().add(world);

		// set up new arrays and objects
		Wall.resetWalls();
		templateWall = new Wall(width, height, posX, posY, wallToSet);
		templateWall.setOpacity(0.5);
		world.getChildren().add(templateWall);

		// sets up the scene
		Scene renderedScene = new Scene(grpGame, 600, 600);
		// sets up the game camera
		camera = new StaticCamera();

		primaryStage.setScene(renderedScene);
		grpGame.setFocusTraversable(true);
		grpGame.requestFocus();
		primaryStage.setWidth(renderedScene.getWindow().getWidth());
		primaryStage.setHeight(renderedScene.getWindow().getHeight());

		Input.addHandlers(primaryStage);

		AnimationTimer mainGameLoop = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				dealWithKeyInput();
				templateWall.setWidth(width);
				templateWall.setHeight(height);
				if (posX < 0) {
					posX = 0;
				}
				if (posY < 0) {
					posY = 0;
				}
				templateWall.setX(posX);
				templateWall.setY(posY);
				templateWall.setImageFromSpriteSheet(wallToSet);
				camera.update(world.getLayoutBounds(), renderedScene.getWindow(), world);
			}
		};
		mainGameLoop.start();
	}

	/**
	 * takes keyboard input and changes the state accordingly
	 */
	private static void dealWithKeyInput() {
		if (Input.isKeyPressed(KeyCode.W)) {
			camera.setPositionY(camera.getPositionY() + 1);
		}
		if (Input.isKeyPressed(KeyCode.S)) {
			camera.setPositionY(camera.getPositionY() - 1);
		}
		if (Input.isKeyPressed(KeyCode.A)) {
			camera.setPositionX(camera.getPositionX() + 1);
		}
		if (Input.isKeyPressed(KeyCode.D)) {
			camera.setPositionX(camera.getPositionX() - 1);
		}
		if (Input.isKeyPressed(KeyCode.Q)) {
			camera.setScaleX(camera.getScaleX() * 1.02);
			camera.setScaleY(camera.getScaleX());
		}
		if (Input.isKeyPressed(KeyCode.E)) {
			camera.setScaleX(camera.getScaleX() / 1.02);
			camera.setScaleY(camera.getScaleX());
		}
		if (Input.isKeyPressed(KeyCode.B)) {
			Input.removeKeyPress(KeyCode.B);
			wallToSet++;
		}
		if (Input.isKeyPressed(KeyCode.LEFT)) {
			Input.removeKeyPress(KeyCode.LEFT);
			posX -= width;
		}
		if (Input.isKeyPressed(KeyCode.RIGHT)) {
			Input.removeKeyPress(KeyCode.RIGHT);
			posX += width;
		}
		if (Input.isKeyPressed(KeyCode.UP)) {
			Input.removeKeyPress(KeyCode.UP);
			posY -= height;
		}
		if (Input.isKeyPressed(KeyCode.DOWN)) {
			Input.removeKeyPress(KeyCode.DOWN);
			posY += height;
		}
		if (Input.isKeyPressed(KeyCode.SPACE)) {
			Input.removeKeyPress(KeyCode.SPACE);
			Wall newWall = new Wall(width, height, posX, posY, wallToSet % 5);
			world.getChildren().add(newWall);
		}

		if (Input.isKeyPressed(KeyCode.ENTER)) {
			Input.removeKeyPress(KeyCode.ENTER);
			printWallsArray();
		}
	}

	/**
	 * prints the array of Wall objects to the console
	 */
	private static void printWallsArray() {
		System.out.println("printWallsArray");
		ArrayList<ArrayList<Wall>> allWalls = new ArrayList<ArrayList<Wall>>();
		ObservableList<Node> worldChildren = world.getChildrenUnmodifiable();
		for (Node n : worldChildren) {
			if (n instanceof Wall) {
				Wall w = (Wall) n;
				if (w != templateWall) {
					double x = w.getX();
					double y = w.getY();
					double width = w.getWidth();
					double height = w.getHeight();
					int ix = (int) (x / width);
					int iy = (int) (y / height);
					System.out.println("[" + ix + ", " + iy + "]: " + w.getWallType());
					System.out.println("pre: allWalls.size(): " + allWalls.size());
					while (allWalls.size() <= iy) {
						allWalls.add(new ArrayList<Wall>());
					}
					while (allWalls.get(iy).size() <= ix) {
						allWalls.get(iy).add(null);
						System.out.println(ix + ", " + allWalls.get(iy).size());
					}
					ArrayList<Wall> wallsX = allWalls.get(iy);
					wallsX.set(ix, w);
					allWalls.set(iy, wallsX);
				}
			}
		}
		allWalls = fillJaggedArray(allWalls);
		Wall[][] allWallsArray = new Wall[allWalls.size()][];
		for (int i = 0; i < allWalls.size(); i++) {
			allWallsArray[i] = allWalls.get(i).toArray(new Wall[0]);
		}
		print2DArray(allWallsArray);
	}

	/**
	 * Converts an array of arrays where children array lengths do not match into
	 * one where they do match
	 * 
	 * @param jaggedArray an array of arrays where the children arrays length's do
	 *                    not match
	 * @return a 2D array where children arrays lengt's math
	 */
	private static ArrayList<ArrayList<Wall>> fillJaggedArray(ArrayList<ArrayList<Wall>> jaggedArray) {
		System.out.println("fillJaggedArray");
		int maxSize = 0;
		for (ArrayList<Wall> ao : jaggedArray) {
			if (ao.size() > maxSize) {
				maxSize = ao.size();
			}
		}
		for (ArrayList<Wall> ao : jaggedArray) {
			while (ao.size() < maxSize) {
				ao.add(null);
			}
		}
		return jaggedArray;
	}

	/**
	 * prints a 2D array of Walls
	 * 
	 * @param array 2D array of Walls
	 */
	private static void print2DArray(Wall[][] array) {
		System.out.println("print2DArray");
		String toPrint = "{";
		for (Wall[] wa : array) {
			toPrint += "{";
			for (Wall w : wa) {
				if (w != null) {
					toPrint += w.getWallType() + 1;
				} else {
					toPrint += "0";
				}
				toPrint += ",";
			}
			if (toPrint.substring(toPrint.length() - 1).equals(",")) {
				toPrint = toPrint.substring(0, toPrint.length() - 1);
			}
			toPrint += "},";
		}
		if (toPrint.substring(toPrint.length() - 1).equals(",")) {
			toPrint = toPrint.substring(0, toPrint.length() - 1);
		}
		toPrint += "}";
		System.out.println(toPrint);
	}

}
