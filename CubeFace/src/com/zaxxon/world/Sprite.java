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

/**
 * An abstract representation of a Sprite to be rendered in a JavaFX Scene
 * 
 * @author Philip Eagles
 *
 */
public abstract class Sprite extends Rectangle {

	/**
	 * used to ensure each Sprite has a unique ID
	 */
	protected static int idCounter = 0;

	/**
	 * the Image texture used by the Sprite
	 */
	protected javafx.scene.image.Image image;
	/**
	 * a spriteSheet of Images used by the Sprite
	 */
	protected javafx.scene.image.Image[] spriteSheet;

	/**
	 * creates a simple Sprite
	 */
	public Sprite() {
		init(0, 0, 0, 0);
	}

	/**
	 * creates a simple Sprite
	 * 
	 * @param width  width of the Sprite
	 * @param height height of the Sprite
	 */
	public Sprite(int width, int height) {
		init(width, height, 0, 0);
	}

	/**
	 * creates a simple Sprite
	 * 
	 * @param width  width of the Sprite
	 * @param height height of the Sprite
	 * @param x      x coordinate of the Sprite
	 * @param y      y coordinate of the Sprite
	 */
	public Sprite(int width, int height, int x, int y) {
		init(width, height, x, y);
	}

	/**
	 * creates a simple Sprite
	 * 
	 * @param width  width of the Sprite
	 * @param height height of the Sprite
	 * @param x      x coordinate of the Sprite
	 * @param y      y coordinate of the Sprite
	 * @param _image the Image to use as the Sprite's texture
	 */
	public Sprite(int width, int height, int x, int y, javafx.scene.image.Image _image) {
		init(width, height, x, y);
		this.image = _image;
	}

	/**
	 * initialises the Sprite by setting up its properties
	 * 
	 * @param width  the width of the Sprite
	 * @param height the height of the Sprite
	 * @param x      x coordinate of the Sprite
	 * @param y      y coordinate of the Sprite
	 */
	private void init(int width, int height, int x, int y) {
		this.setId(Integer.toString(idCounter));
		idCounter++;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}

	/**
	 * removes the Sprite from the game and from all references
	 */
	public abstract void delete();

	public void setImageSpriteSheet(BufferedImage image, int rows, int columns) {
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

	public void setImage(BufferedImage i) {
		setImage(SwingFXUtils.toFXImage(i, null));
	}

	public void setImageSpriteSheet(Image imageFile, int rows, int columns) {
		BufferedImage image = (BufferedImage) imageFile;
		setImageSpriteSheet(image, rows, columns);
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

	/**
	 * returns the distance between this Sprite and another Sprite
	 * 
	 * @param s the SPrite to measure the distance to
	 * @return the distance between Sprites (in terms of pixels)
	 */
	public double getDistanceToSprite(Sprite s) {
		double differenceX = s.getX() - this.getX();
		double differenceY = s.getY() - this.getY();
		differenceX *= differenceX;
		differenceY *= differenceY;
		return Math.sqrt(differenceX + differenceY);
	}

	/**
	 * whether the Sprite is considered alive (non-alive Sprites are may include
	 * Sprites with no health property)
	 * 
	 * @return true if alive else false
	 */
	public abstract Boolean isAlive();

}