package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;

//Written by Dan

public class Weapon_MG extends Weapon {
	
	private final double damage = 4;
	
	public Weapon_MG() {
		
		super.weaponName = "Machine Gun";
		super.bulletDamage = damage;
		
	}
	
	@Override
	public Bullet fire(Vector2 dir, Vector2 weaponPos) {
		
		Bullet bullet = new Bullet(dir, weaponPos, bulletDamage);
		return bullet;
	}
}
