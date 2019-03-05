package com.zaxxon.world.mobile.enemies;

import java.util.ArrayList;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;

import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon {

	private FacingDir facingDir;
	private Vector2 dir;
	private Vector2 playerPos;
	private Vector2 weaponPos;
	
	Boolean fired = false;

	
	private ArrayList<Bullet> allBullets;
	
	public Weapon () {
		
		allBullets = new ArrayList<Bullet>();
	}
	
	public void fire() {
		
		Bullet bullet = new Bullet(dir, weaponPos);
		allBullets.add(bullet);
		fired = true;
		
	}
	
	private Vector2 getWeaponPos(Vector2 playerPos, Vector2 playerDimensions, Vector2 dir) {
		
		switch (facingDir) {
		
    	case up:
    		return new Vector2(playerPos.x + playerDimensions.x/1.3, playerPos.y + playerDimensions.y/3);
    		
    	case down:
    		return new Vector2(playerPos.x + playerDimensions.x/8, playerPos.y + playerDimensions.y/1.5);
    		
    	case left:
    		return new Vector2(playerPos.x + playerDimensions.x/16, playerPos.y + playerDimensions.y/2.8);
    		
    	case right:
    		return new Vector2(playerPos.x + playerDimensions.x/1.4, playerPos.y + playerDimensions.y/1.8);
    		
    	default:
    		return playerPos;
    	}
		
		
	}
	
	public void update(double deltaTime, Vector2 playerPos, Vector2 playerDimensions, FacingDir facingDir) {
		
		this.playerPos = playerPos;
		this.dir = getFacingDirAsVector(facingDir);
		this.facingDir = facingDir;
		
		if (Input.isKeyPressed(KeyCode.SPACE)) {
    		
			if (!fired) {
				
				this.weaponPos = getWeaponPos(playerPos, playerDimensions, dir);
	    		fire();
			}
    	}
		
		else {
			
			fired = false;
		}
		
		for (int i = 0; i < allBullets.size(); i++) {
			
			allBullets.get(i).update(deltaTime);
		}
	}
	
	 public Vector2 getFacingDirAsVector(FacingDir facingDir) {
	    	
	    	switch (facingDir) {
	    		
	    	case up:
	    		return new Vector2(0, -1);
	    		
	    	case down:
	    		return new Vector2(0, 1);
	    		
	    	case left:
	    		return new Vector2(-1, 0);
	    		
	    	case right:
	    		return new Vector2(1, 0);
	    		
	    	default:
	    		return new Vector2();
	    	}
	    }
}
