package com.zaxxon.world.shooting;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import com.zaxxon.sound.MusicPlayer;
import com.zaxxon.world.mobile.Player;
import javafx.scene.input.KeyCode;

//Written by Dan

public class Weapon_MG extends Weapon {

	private final double damage = 4;
	private final double despawnDistance = 1000;
	public static MusicPlayer sound = new MusicPlayer("/mainmenu/LaserShoot_2.wav");

	public Weapon_MG(Player player) {
		super(player);
		super.weaponName = "Machine Gun";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;

	}

	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {

		if (multiplayer) {

			Bullet bullet = new Bullet(dir, weaponPos, bulletDamage, despawnDistance, player);
			WeaponManager.addBulletToList(bullet);
		}

		else if (Input.isKeyPressed(KeyCode.SPACE) && (super.test == false)) {

			sound.play();
			Bullet bullet = new Bullet(dir, weaponPos, bulletDamage, despawnDistance, player);

			WeaponManager.addBulletToList(bullet);
		}
	}
}
