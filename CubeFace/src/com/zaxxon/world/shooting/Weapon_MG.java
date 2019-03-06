package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;

public class Weapon_MG extends Weapon {
	
	public Weapon_MG() {
		
		weaponName = "Machine Gun";
	}
	
	@Override
	public Bullet fire(Vector2 dir, Vector2 weaponPos) {
		
		Bullet bullet = new Bullet(dir, weaponPos);
		return bullet;
	}
}
