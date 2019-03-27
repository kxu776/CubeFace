package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.Player;


/**
 * @author Dan
 *
 *	Generic weapon class that each specific weapon extends for their own behaviour
 */
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
	
	/**
	 * Create a new weapon object
	 * @param player - player the weapon belongs to
	 */
	public Weapon (Player player) {
		this.player = player;
	}
	
	/**
	 * Fires a bullet (specific behaviour overridden in each gun)
	 * @param dir - direction to shoot 
	 * @param weaponPos - position of the weapon
	 * @param multiplayer - boolean to check if being called in a multiplayer context
	 */
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
	}
	
	/**
	 * @return weapon name
	 */
	public String getWeaponName() {
		
		return weaponName;
	}
	
	/**
	 * @return ammo
	 */
	public String getAmmo() {

		return Integer.toString(((int) currentAmmo));
	}
	
	/**
	 * adds the specified ammo amount to the current ammo count up to the maximum holdable
	 * @param amount
	 */
	public void addAmmo (double amount) {
		
		currentAmmo = Math.min(maxAmmo, currentAmmo + amount);
	}
	
}
