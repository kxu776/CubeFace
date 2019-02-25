package com.zaxxon.world.mobile.enemies;

import java.util.ArrayList;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;

import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon {

	private Vector2 dir;
	private Vector2 playerPos;
	private Vector2 weaponPos;
	
	private ArrayList<Bullet> allBullets;
	
	public Weapon () {
		
		allBullets = new ArrayList<Bullet>();
	}
	
	public void fire() {
		
		Bullet bullet = new Bullet(dir, weaponPos);
		allBullets.add(bullet);
		
		System.out.println("Bullet created");
	}
	
	private Vector2 getWeaponPos(Vector2 playerPos, Vector2 dir) {
		
		return playerPos;
	}
	
	public void update(double deltaTime, Vector2 playerPos, Vector2 dir) {
		
		this.playerPos = playerPos;
		this.dir = dir;
		
		if (Input.isKeyPressed(KeyCode.SPACE)) {
    		
			this.weaponPos = getWeaponPos(playerPos, dir);
    		fire();
    	}
		
		for (int i = 0; i < allBullets.size(); i++) {
			
			allBullets.get(i).update(deltaTime);
		}
	}
}
