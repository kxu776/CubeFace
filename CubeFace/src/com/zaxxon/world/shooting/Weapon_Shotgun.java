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
 * Class for updating and firing the shotgun
 */
public class Weapon_Shotgun extends Weapon {
	
	private final double damage = 8;
	private final double despawnDistance = 200;
	private final double maxAmmo = 40;

	public static MusicPlayer sound = new MusicPlayer("/mainmenu/Gun_Shoot_1.wav");
	private Boolean fired = false;
	
	/**
	 * Create an initialise a new shotgun
	 * @param player - player the gun belongs to
	 */
	public Weapon_Shotgun(Player player) {
		super(player);
		super.weaponName = "Shotgun";
		super.bulletDamage = damage;
		super.despawnDistance = despawnDistance;
		super.maxAmmo = maxAmmo;
		
		addAmmo (maxAmmo);
	}
	
	@Override
	public void fire(Vector2 dir, Vector2 weaponPos, Boolean multiplayer) {
		
		if (multiplayer || Input.isKeyPressed(KeyCode.SPACE) && (super.test == false)) {
			
			if (!fired) {
				
				if (currentAmmo >= 4) {
					
					if (Math.abs(dir.y) > 0) {
						
						Bullet b0 = new Bullet(new Vector2 (dir.x-0.2, dir.y), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b0);
						
						Bullet b1 = new Bullet(new Vector2 (dir.x-0.5, dir.y), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b1);
						
						Bullet b2 = new Bullet(new Vector2 (dir.x+0.2, dir.y), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b2);
						
						Bullet b3 = new Bullet(new Vector2 (dir.x+0.5, dir.y), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b3);
					}
					
					else {

						Bullet b0 = new Bullet(new Vector2 (dir.x, dir.y-0.2), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b0);
						
						Bullet b1 = new Bullet(new Vector2 (dir.x, dir.y-0.5), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b1);
						
						Bullet b2 = new Bullet(new Vector2 (dir.x, dir.y+0.2), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b2);
						
						Bullet b3 = new Bullet(new Vector2 (dir.x, dir.y+0.5), weaponPos, bulletDamage, despawnDistance,player);
						WeaponManager.addBulletToList(b3);
					}


					if (!MainGame.muted) {
						
						sound.shoot();
					}
					

					currentAmmo-=4;
					
					
					fired = true;
				}
			}	
		}
		
		else {
			
			fired = false;
		}
		
		player.weaponManager.updateWeaponReadout();
	}
}
