package com.zaxxon.world.shooting;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon_Shotgun extends Weapon {
	
	private final double damage = 8;
	
	private Boolean fired = false;
	
	public Weapon_Shotgun() {
		
		super.weaponName = "Shotgun";
		super.bulletDamage = damage;
	}
	
	@Override
	public void fire(Vector2 dir, Vector2 weaponPos) {
		
		if (Input.isKeyPressed(KeyCode.SPACE)) {
			
			if (!fired) {
				
				if (Math.abs(dir.y) > 0) {
					
					Bullet b0 = new Bullet(new Vector2 (dir.x-0.2, dir.y), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x-0.5, dir.y), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x+0.2, dir.y), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x+0.5, dir.y), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b3);
				}
				
				else {
					
					Bullet b0 = new Bullet(new Vector2 (dir.x, dir.y-0.2), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x, dir.y-0.5), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x, dir.y+0.2), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x, dir.y+0.5), weaponPos, bulletDamage);
					WeaponManager.addBulletToList(b3);
				}
				
				
				
				
				fired = true;
			}
		}
		
		else {
			
			fired = false;
		}
	}
}
