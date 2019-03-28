package com.zaxxon.world.shooting;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;
import com.zaxxon.world.pickups.PickupPoint;

/**
 * Class to create and update pickups Type 0,1,2 corresponds to MG ammo, SG
 * ammo, and health respectively
 */
public class AmmoPickup extends Sprite {

	public int type; // 0 is MG, 1 is SG, 2 is health
	private long spawnedTime;
	private final long existTime = 1000 * 16;
	private PickupPoint pickupPoint; // MP spawn location

	/**
	 * Create and initialise a new pickup
	 * 
	 * @param type        - type of pick up
	 * @param pos         - position to spawn at
	 * @param pickupPoint - multiplayer position
	 */
	public AmmoPickup(int type, Vector2 pos, PickupPoint pickupPoint) {
		if (type == 2) {
			this.setWidth(26);
			this.setHeight(23);
		} else {
			this.setWidth(64);
			this.setHeight(32);
		}
		this.setX(pos.x);
		this.setY(pos.y);

		if (MainGame.multiplayer) {
			this.pickupPoint = pickupPoint;
		}

		spawnedTime = System.currentTimeMillis();
		this.type = type;

		if (type == 0) {
			setImage(SpriteImages.MGAP_IMAGE);
		} else if (type == 1) {
			setImage(SpriteImages.SGAP_IMAGE);
		} else {
			setImage(SpriteImages.HEART_IMAGE);
		}
	}

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
		MainGame.ammoPickupList.remove(this);
		if (pickupPoint != null) {
			pickupPoint.startSpawnTimer();
		}
	}

	@Override
	public Boolean isAlive() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Update the pickup so it despawns after a certain amount of time It flashes
	 * (via opacity setting) prior to despawning
	 */
	public void update() {
		long elapsedTime = System.currentTimeMillis() - spawnedTime;
		if (elapsedTime >= existTime) {
			this.setOpacity(0.3);
			if (elapsedTime >= existTime * 1.5f) {
				delete();
			}
		}
	}

}
