package com.zaxxon.world.shooting;

import com.zaxxon.client.MainGame;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import com.zaxxon.sound.MusicPlayer;
import com.zaxxon.world.mobile.Player;
import javafx.scene.input.KeyCode;


/**
 * @author Dan
 *
 * Class for updating and firing the pistol
 */
public class Weapon_Default extends Weapon {

	private final double damage = 10;
	private final double despawnDistance = 600;
	public static MusicPlayer sound = new MusicPlayer("/mainmenu/LaserShoot_2.wav");
	private Boolean fired = false;

	/**
	 * Create and initialise a new pistol
	 * @param player - player the gun belongs to
	 */
	public Weapon_Default(Player player) {
		super(player);
		super.weaponName = "Pistol";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;
		super.player = player;

	}

	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {

		if (multiplayer || (Input.isKeyPressed(KeyCode.SPACE) && (super.test == false))) {

			if (!fired) {

				if (!MainGame.muted) {
					
					sound.shoot();
				}
				
				Bullet bullet = new Bullet(dir, weaponPos, bulletDamage, despawnDistance, player);
				WeaponManager.addBulletToList(bullet);
				fired = true;
			}
		}

		else {

			fired = false;
		}
	}
	
	@Override
	public String getAmmo() {
		return "Unlimited";
	}
}
