package com.zaxxon.world.mobile.enemies;

import com.zaxxon.gameart.SpriteImages;

public class Zombie extends Enemy {

	public Zombie(double spawnX, double spawnY) {
        controllable = false;
        this.setX(spawnX);
        this.setY(spawnY);
        setImageSpriteSheet(SpriteImages.ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
        setImageFromSpriteSheet(0);
        this.setWidth(width);
        this.setHeight(height);
        facingDir = Enemy.FacingDir.up;
        isAlive = true;
        health = 100.0;
        pathfinding = false;
	}

	@Override
	protected void attack() {
		// TODO Auto-generated method stub
		
	}

}
