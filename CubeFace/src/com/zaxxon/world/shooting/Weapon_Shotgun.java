package com.zaxxon.world.shooting;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import com.zaxxon.sound.MusicPlayer;
import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon_Shotgun extends Weapon {
	
	private final double damage = 8;
	private final double despawnDistance = 200;

	public static MusicPlayer sound = new MusicPlayer("/mainmenu/Gun_Shoot_1.wav");
	private Boolean fired = false;
	
	public Weapon_Shotgun() {
		
		super.weaponName = "Shotgun";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;

	}
	
	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
		if (multiplayer) {
			
			if (!fired) {
				
				if (Math.abs(dir.y) > 0) {
					
					Bullet b0 = new Bullet(new Vector2 (dir.x-0.2, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x-0.5, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x+0.2, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x+0.5, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b3);
				}
				
				else {

					sound.play();

					Bullet b0 = new Bullet(new Vector2 (dir.x, dir.y-0.2), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x, dir.y-0.5), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x, dir.y+0.2), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x, dir.y+0.5), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b3);
				}
				
				
				
				
				fired = true;
			}	
		}
		
		else if (Input.isKeyPressed(KeyCode.SPACE)&& !super.test) {
			
			if (!fired) {
				
				if (Math.abs(dir.y) > 0) {
					
					Bullet b0 = new Bullet(new Vector2 (dir.x-0.2, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x-0.5, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x+0.2, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x+0.5, dir.y), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b3);
				}
				
				else {
					
					Bullet b0 = new Bullet(new Vector2 (dir.x, dir.y-0.2), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b0);
					
					Bullet b1 = new Bullet(new Vector2 (dir.x, dir.y-0.5), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b1);
					
					Bullet b2 = new Bullet(new Vector2 (dir.x, dir.y+0.2), weaponPos, bulletDamage, despawnDistance);
					WeaponManager.addBulletToList(b2);
					
					Bullet b3 = new Bullet(new Vector2 (dir.x, dir.y+0.5), weaponPos, bulletDamage, despawnDistance);
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
