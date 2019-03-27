package com.zaxxon.world.mobile.enemies;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

/**
 * A special enemy type - A Hunter possesses less health, but faster movement
 * than a base zombie enemy. Furthermore, the reward for killing a hunter is
 * greater than that of a base zombie.
 */
public class Hunter extends Enemy {
	

	protected static final int TARGET_WIDTH = 48;
	protected static final int TARGET_HEIGHT = 48;

	/**
	 * Class constructor - specifies spawn coordinates
	 *
	 * @param spawnX x-coordinate of spawn location
	 * @param spawnY y-coordinated of spawn location
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
		health = 30.0;
		damage *= 0.8;
		maxSpeed *= 1.4;
		killReward = 750;
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
