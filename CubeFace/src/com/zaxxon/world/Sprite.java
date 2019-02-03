package com.zaxxon.world;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

public class Sprite extends Rectangle {

	protected static int idCounter = 0;

	protected javafx.scene.image.Image image;
	protected javafx.scene.image.Image[] spriteSheet;

	public Sprite() {
		init(0, 0, 0, 0);
	}

	public Sprite(int width, int height) {
		init(width, height, 0, 0);
		Rectangle r = new Rectangle();
	}

	public Sprite(int width, int height, int x, int y) {
		init(width, height, x, y);
	}
	
	private void init(int width, int height, int x, int y) {
		this.setId(Integer.toString(idCounter));
		idCounter++;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}

	public void setImageSpriteSheet(Image imageFile, int rows, int columns) {
		BufferedImage image = (BufferedImage) imageFile;
		int width = image.getWidth() / columns;
		int height = image.getHeight() / rows;
		spriteSheet = new javafx.scene.image.Image[rows * columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				spriteSheet[(i * columns) + j] = SwingFXUtils
						.toFXImage(image.getSubimage(j * width, i * height, width, height), null);
			}
		}
	}

	public void setImageSpriteSheet(String imageURL, int rows, int columns) throws IOException {
		File imageFile = new File(imageURL);
		setImageSpriteSheet(ImageIO.read(imageFile), rows, columns);
	}

	public void setImage(javafx.scene.image.Image i) {
		image = i;
		ImagePattern imgPat = new ImagePattern(i);
		this.setFill(imgPat);
	}

	public void setImage(String filename) {
		javafx.scene.image.Image i = new javafx.scene.image.Image(filename);
		setImage(i);
	}

	public void setImageFromSpriteSheet(int index) {
		setImage(spriteSheet[index % spriteSheet.length]);
	}

	public String toString() {
		return "ID: " + this.getId() + ", " + super.toString();
	}
	
	public javafx.scene.image.Image[] getSpriteSheet() {
		return spriteSheet;
	}

	public LinkedHashMap<String, Object> getAttributes() {
		LinkedHashMap<String, Object> attributes = new LinkedHashMap<>();
		attributes.put("posX", this.getX());
		attributes.put("posY", this.getY());
		attributes.put("height", this.getHeight());
		attributes.put("width", this.getWidth());
		attributes.put("id", this.getId());
		return attributes;
	}

}