package com.zaxxon.world.mobile.enemies;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *  A special enemy type - A Hunter possess less health, but faster movement than a base zombie enemy. Furthermore, the reward for killing a hunter is greater than that of a base zombie.
 */
public class Hunter extends Enemy {

	/**
	 * Class constructor - specifies spawn coordinates
	 *
	 * @param spawnX	x-coordinate of spawn location
	 * @param spawnY	y-coordinated of spawn location
	 */
	public Hunter(double spawnX, double spawnY) {
		controllable = false;
		this.setX(spawnX);
		this.setY(spawnY);
		setImageSpriteSheet(SpriteImages.HUNTER_ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
		setImageFromSpriteSheet(0);
		this.setWidth(TARGET_WIDTH);
		this.setHeight(TARGET_HEIGHT);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		health = 50.0;
		killReward = 750;
	}

	/**
	 * Class destructor - removes all references to object instance in order to un-instantiate object.
	 */
	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}
}
