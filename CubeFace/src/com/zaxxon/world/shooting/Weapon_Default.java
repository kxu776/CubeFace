package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;

public class Weapon_Default extends Weapon {

	public Weapon_Default() {
		
		weaponName = "Pistol";
	}
	
	@Override
	public Bullet fire(Vector2 dir, Vector2 weaponPos) {
		
		Bullet bullet = new Bullet(dir, weaponPos);
		return bullet;
	}
}
