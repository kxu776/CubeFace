/**
 * 
 */
package com.zaxxon.world.mobile.enemies;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;

/**
 * A special enemy type - A Brute possesses more health, but slower movement
 * than a base zombie enemy. Furthermore, the reward for killing a hunter is
 * greater than that of a base zombie.
 */
public class Brute extends Enemy {
	

	protected static final int TARGET_WIDTH = 80;
	protected static final int TARGET_HEIGHT = 80;

	/**
	 * Class constructor - specifies spawn coordinates
	 *
	 * @param spawnX x-coordinate of spawn location
	 * @param spawnY y-coordinated of spawn location
	 */
	public Brute(double spawnX, double spawnY) {
		controllable = false;
		this.setX(spawnX);
		this.setY(spawnY);
		setImageSpriteSheet(SpriteImages.BRUTE_ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
		setImageFromSpriteSheet(0);
		this.setWidth(TARGET_WIDTH);
		this.setHeight(TARGET_HEIGHT);
		facingDir = Enemy.FacingDir.up;
		isAlive = true;
		pathfinding = false;
		health = 140.0;
		damage *= 2;
		maxSpeed *= 0.6;
		killReward = 850;
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
