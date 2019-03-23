package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.Player;

//Written by Dan

public class Weapon {

	double bulletDamage;
	double bulletSpeed;
	double bulletAngle;
	double despawnDistance;
	double maxAmmo;
	double currentAmmo;
	String weaponName;
	public Boolean test = false;

	Player player;
	
	public Weapon (Player player) {
		this.player = player;
	}
	
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
	}
	
	public String getWeaponName() {
		
		return weaponName;
	}
	
	public String getAmmo() {
		
		return Double.toString(currentAmmo);
	}
	
	public void addAmmo (double amount) {
		
		currentAmmo = Math.min(maxAmmo, currentAmmo + amount);
	}
	
}
