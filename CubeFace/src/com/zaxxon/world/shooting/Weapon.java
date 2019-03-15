package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.Player;

//Written by Dan

public class Weapon {

	double bulletDamage;
	double bulletSpeed;
	double bulletAngle;
	double despawnDistance;
	String weaponName;
	Player player;
	
	public Weapon (Player player) {
		this.player = player;
	}
	
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
	}
	
	public String getWeaponName() {
		
		return weaponName;
	}
	
}
