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
 * Class for updating and firing the machine gun
 */
public class Weapon_MG extends Weapon {

	private final double damage = 2;
	private final double despawnDistance = 1000;
	private final double maxAmmo = 160;
	public static MusicPlayer sound = new MusicPlayer("/mainmenu/Gun_Shoot_2.wav");

	/**
	 * Create and initialise a new machine gun
	 * @param player - player the gun belongs to
	 */
	public Weapon_MG(Player player) {
		super(player);
		super.weaponName = "Machine Gun";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;
		super.maxAmmo = maxAmmo;
		
		addAmmo (maxAmmo);

	}

	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {

		if (multiplayer || Input.isKeyPressed(KeyCode.SPACE) && (super.test == false)) {

			if (currentAmmo > 0) {
				
				if (!MainGame.muted) {
					
					sound.shoot();
				}

				currentAmmo--;

				Bullet bullet = new Bullet(dir, weaponPos, bulletDamage, despawnDistance, player);
				WeaponManager.addBulletToList(bullet);
			}
			
			

		}

		player.weaponManager.updateWeaponReadout();
	}
}
