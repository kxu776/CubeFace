package com.zaxxon.gameart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteImages {

	public static final String BRUTE_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/brute-zombie.png";
	public static final String CUBEFACE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/cubeface-sprite-sheet.png";
	public static final String HUNTER_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/hunter-zombie-sprite-sheet.png";
	public static final String MUTANT_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/mutant-zombie-sprite-sheet.png";
	public static final String WALL_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/wall-sprite-sheet.png";
	public static final String ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/zombie-sprite-sheet.png";
	public static final String BULLET_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/bullet.png";


	public static final BufferedImage BRUTE_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			BRUTE_ZOMBIE_SPRITESHEET_URL);
	public static final BufferedImage CUBEFACE_SPRITESHEET_IMAGE = getBufferedImageFromURL(CUBEFACE_SPRITESHEET_URL);
	public static final BufferedImage HUNTER_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			HUNTER_ZOMBIE_SPRITESHEET_URL);
	public static final BufferedImage MUTANT_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			MUTANT_ZOMBIE_SPRITESHEET_URL);
	public static final BufferedImage WALL_SPRITESHEET_IMAGE = getBufferedImageFromURL(WALL_SPRITESHEET_URL);
	public static final BufferedImage ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(ZOMBIE_SPRITESHEET_URL);
	public static final BufferedImage BULLET_SPRITESHEET_IMAGE = getBufferedImageFromURL(BULLET_SPRITESHEET_URL);

	private static BufferedImage getBufferedImageFromURL(String url) {
		File imageFile = new File(url);
		try {
			return ImageIO.read(imageFile);
		} catch (IOException e) {
			System.err.println("Critical error reading image file " + url);
			e.printStackTrace();
			System.exit(2);
			return null;
		}
	}

}
