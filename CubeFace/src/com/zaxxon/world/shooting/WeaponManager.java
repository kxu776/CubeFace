package com.zaxxon.world.shooting;

import java.util.ArrayList;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;

import javafx.scene.input.KeyCode;

//Written by Dan

public class WeaponManager {

	private FacingDir facingDir;
	public Vector2 dir;
	public Vector2 playerPos;
	private Vector2 weaponPos;
	

	int currentWeapon = 0;
	private ArrayList<Weapon> weapons;
	private static ArrayList<Bullet> allBullets;
	
	public WeaponManager () {  
		
		weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon_Default());
		weapons.add(new Weapon_MG());
		weapons.add(new Weapon_Shotgun());
		
		allBullets = new ArrayList<Bullet>();
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
		
		this.playerPos = (playerPos);
		this.dir = getFacingDirAsVector(facingDir);
		this.facingDir = facingDir;
		

		if (Input.isKeyPressed(KeyCode.SHIFT)) {
			
			ChangeWeapon();
		}
		
		this.weaponPos = getWeaponPos(playerPos, playerDimensions, dir);
	    weapons.get(currentWeapon).fire(dir, weaponPos, false);
    	
		
		for (int i = 0; i < allBullets.size(); i++) {
			
			allBullets.get(i).update(deltaTime);
		}
	}
	
	public void ChangeWeapon() {
		
		if (Input.isKeyPressed(KeyCode.DIGIT1)) {
			
			currentWeapon = 0;
		}
		
		else if (Input.isKeyPressed(KeyCode.DIGIT2)) {
					
			currentWeapon = 1;
		}

		else if (Input.isKeyPressed(KeyCode.DIGIT3)) {
			
			currentWeapon = 2;
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
	 public Vector2 getPlayerPos() {
		return playerPos;
		 
	 }
	

	 
	 
	 public String getCurrentWeaponName() {
			
		return weapons.get(currentWeapon).getWeaponName();
	 }
	 
	 public static void addBulletToList(Bullet b) {
		 allBullets.add(b);
	 }
	 
	 public static void removeBulletFromList(Bullet b) {
		 allBullets.remove(b);
	 }
	 
	 public Weapon getCurrentWeapon() {
		 return weapons.get(currentWeapon);
	 }
	  
}
