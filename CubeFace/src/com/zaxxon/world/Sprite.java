package com.zaxxon.world;

import javafx.scene.canvas.GraphicsContext;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.LinkedHashMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;

public class Sprite {

	protected static int idCounter = 0;

	protected javafx.scene.image.Image image;
	protected javafx.scene.image.Image[] spriteSheet;
	protected int positionX;
	protected int positionY;
	protected int width;
	protected int height;
	protected int id;

	public Sprite() {
		id = idCounter;
		idCounter++;
		positionX = 0;
		positionY = 0;
	}

	public void setImageSpriteSheet(Image im, int rows, int columns) {
		BufferedImage image = (BufferedImage) im;
		javafx.scene.image.Image[] spriteSheet = new javafx.scene.image.Image[rows * columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				spriteSheet[(i * columns) + j] = SwingFXUtils
						.toFXImage(image.getSubimage(j * width, i * height, width, height), null);
			}
		}
	}

	public void setImage(javafx.scene.image.Image i) {
		image = i;
		width = (int) i.getWidth();
		height = (int) i.getHeight();
	}

	public void setImage(String filename) {
		javafx.scene.image.Image i = new javafx.scene.image.Image(filename);
		setImage(i);
	}

	public void setImageFromSpriteSheet(int index) {
		setImage(spriteSheet[index]);
	}

	public void setPosition(int x, int y) {
		positionX = x;
		positionY = y;
	}
	
	public int getPositionX() {
		return positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, positionX, positionY);
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}

	public boolean intersects(Sprite s) {
		return s.getBoundary().intersects(this.getBoundary());
	}

	public String toString() {
		return "ID: " + id + ", Position: [" + positionX + ", " + positionY + "], Dimensions: [" + width + ", " + height
				+ "]";
	}


	public LinkedHashMap<String,Object> getAttributes(){
		LinkedHashMap<String,Object> attributes = new LinkedHashMap<>();
		attributes.put("posX", positionX);
		attributes.put("posY", positionY);
		attributes.put("height", height);
		attributes.put("width", width);
		return attributes;
	}

}