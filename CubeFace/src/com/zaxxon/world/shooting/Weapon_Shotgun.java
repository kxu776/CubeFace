package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;

//Written by Dan

public class Weapon_Shotgun extends Weapon {
	
	private final double damage = 8;
	
	public Weapon_Shotgun() {
		
		super.weaponName = "Shotgun";
		super.bulletDamage = damage;
	}
	
	@Override
	public void fire(Vector2 dir, Vector2 weaponPos) {
		
		Bullet bullet = new Bullet(dir, weaponPos, bulletDamage);
		WeaponManager.addBulletToList(bullet);
	}
}
