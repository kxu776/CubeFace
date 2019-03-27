package com.zaxxon.world.mobile.enemies;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

/**
 * A base enemy instance. This possesses default health, damage and movement
 * characteristics.
 */
public class Zombie extends Enemy {

	/**
	 * Class constructor - specifies spawn coordinates
	 *
	 * @param spawnX x-coordinate of spawn location
	 * @param spawnY y-coordinated of spawn location
	 */
	public Zombie(double spawnX, double spawnY) {
		controllable = false;
		this.setX(spawnX);
		this.setY(spawnY);
		setImageSpriteSheet(SpriteImages.ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
		setImageFromSpriteSheet(0);
		this.setWidth(TARGET_WIDTH);
		this.setHeight(TARGET_HEIGHT);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		health = 80;
		killReward = 500;
		setDifficultyScaling(System.currentTimeMillis() - MainGame.getGameStartTime());
	}

	/**
	 * Class destructor - removes all references to object instance in order to
	 * un-instantiate object.
	 */
	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}

}
