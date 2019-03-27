package com.zaxxon.gameart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * URLs and BufferedImages for relevant game textures
 * 
 * @author Philip Eagles
 *
 */
public class SpriteImages {

	/**
	 * String URL for the brute zombie spritesheet
	 */
	public static final String BRUTE_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/brute-zombie.png";
	/**
	 * String URL for the player spritesheet
	 */
	public static final String CUBEFACE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/cubeface-sprite-sheet.png";
	/**
	 * String URL for the hunter zombie spritesheet
	 */
	public static final String HUNTER_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/hunter-zombie-sprite-sheet.png";
	/**
	 * String URL for the mutant zombie spritesheet
	 */
	public static final String MUTANT_ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/mutant-zombie-sprite-sheet.png";
	/**
	 * String URL for the wall spritesheet
	 */
	public static final String WALL_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/wall-sprite-sheet.png";
	/**
	 * String URL for the zombie spritesheet
	 */
	public static final String ZOMBIE_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/zombie-sprite-sheet.png";
	/**
	 * String URL for the bullet spritesheet
	 */
	public static final String BULLET_SPRITESHEET_URL = "CubeFace/src/com/zaxxon/gameart/bullet.png";
	/**
	 * String URL for the background tile spritesheet
	 */
	public static final String BACKGROUND_TILE_URL = "CubeFace/src/com/zaxxon/ui/img/ground.png";
	/**
	 * String URL for machine gun ammo pickup
	 */
	public static final String MGAP_URL = "CubeFace/src/com/zaxxon/gameart/machinegun.png";
	/**
	 * String URL for shotgun ammo pickup
	 */
	public static final String SGAP_URL = "CubeFace/src/com/zaxxon/gameart/shotgun.png";
	/**
	 * String URL for health pickup
	 */
	public static final String HEART_URL = "CubeFace/src/com/zaxxon/gameart/heart.png";

	/**
	 * BufferedImage from the brute zombie URL
	 */
	public static final BufferedImage BRUTE_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			BRUTE_ZOMBIE_SPRITESHEET_URL);
	/**
	 * BufferedImage from the player URL
	 */
	public static final BufferedImage CUBEFACE_SPRITESHEET_IMAGE = getBufferedImageFromURL(CUBEFACE_SPRITESHEET_URL);
	/**
	 * BufferedImage from the hunter zombie URL
	 */
	public static final BufferedImage HUNTER_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			HUNTER_ZOMBIE_SPRITESHEET_URL);
	/**
	 * BufferedImage from the mutant zombie URL
	 */
	public static final BufferedImage MUTANT_ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(
			MUTANT_ZOMBIE_SPRITESHEET_URL);
	/**
	 * BufferedImage from the wall URL
	 */
	public static final BufferedImage WALL_SPRITESHEET_IMAGE = getBufferedImageFromURL(WALL_SPRITESHEET_URL);
	/**
	 * BufferedImage from the zombie URL
	 */
	public static final BufferedImage ZOMBIE_SPRITESHEET_IMAGE = getBufferedImageFromURL(ZOMBIE_SPRITESHEET_URL);
	/**
	 * BufferedImage from the bullet URL
	 */
	public static final BufferedImage BULLET_SPRITESHEET_IMAGE = getBufferedImageFromURL(BULLET_SPRITESHEET_URL);
	/**
	 * BufferedImage from the background tile URL
	 */
	public static final BufferedImage BACKGROUND_TILE_IMAGE = getBufferedImageFromURL(BACKGROUND_TILE_URL);
	/**
	 * BufferedImage from the MGAP URL
	 */
	public static final BufferedImage MGAP_IMAGE = getBufferedImageFromURL(MGAP_URL);
	/**
	 * BufferedImage from the SGAP URL
	 */
	public static final BufferedImage SGAP_IMAGE = getBufferedImageFromURL(SGAP_URL);
	/**
	 * BufferedImage from the SGAP URL
	 */
	public static final BufferedImage HEART_IMAGE = getBufferedImageFromURL(HEART_URL);

	/**
	 * @param url a String of the file to read as an image
	 * @return a BufferedImage object from the URL String
	 */
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
