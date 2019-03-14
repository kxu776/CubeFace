package com.zaxxon.world.shooting;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import com.zaxxon.world.mobile.Player;
import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon_Default extends Weapon {

	private final double damage = 10;
	private final double despawnDistance = 600;
	
	private Boolean fired = false;
	
	public Weapon_Default(Player player) {
		super(player);
		super.weaponName = "Pistol";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;
		super.player = player;
		
	}
	
	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
		if (multiplayer || Input.isKeyPressed(KeyCode.SPACE)) {
			
			if (!fired) {
				
				Bullet bullet = new Bullet(dir, weaponPos, bulletDamage, despawnDistance, player);
				WeaponManager.addBulletToList(bullet);
				fired = true;
			}
		}
		
		else {
			
			fired = false;
		}
	}
}
