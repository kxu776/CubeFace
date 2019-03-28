package com.zaxxon.world.shooting;

import java.util.ArrayList;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.ui.tools.StatsBox;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;

import com.zaxxon.world.mobile.Player;
import javafx.scene.input.KeyCode;


/**
 * @author Dan
 *
 *	Class that holds each individual weapon and updates them and their associated bullets
 */
public class WeaponManager {

	private FacingDir facingDir;
	public Vector2 dir;
	public Vector2 playerPos;
	public boolean mp = false;
	private Vector2 weaponPos;

	int currentWeapon = 0;
	private ArrayList<Weapon> weapons;
	private static ArrayList<Bullet> allBullets;

	public WeaponManager(){};
	
	/**
	 * Creates a new weapon manager object and creates a new weapon of each type to store
	 * @param player - player the weapons belong to
	 */
	public WeaponManager (Player player) {
		
		weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon_Default(player));
		weapons.add(new Weapon_MG(player));
		weapons.add(new Weapon_Shotgun(player));
		

		allBullets = new ArrayList<Bullet>();
	}

	/**
	 * Calculates the position the player graphic holds the gun in
	 * @param playerPos - player's position
	 * @param playerDimensions - player's size
	 * @param dir - direction the player is facing
	 * @return calculated position
	 */
	public Vector2 getWeaponPos(Vector2 playerPos, Vector2 playerDimensions, Vector2 dir) {

		switch (facingDir) {

			case up:
				return new Vector2(playerPos.x + playerDimensions.x / 1.3, playerPos.y + playerDimensions.y / 3);

			case down:
				return new Vector2(playerPos.x + playerDimensions.x / 8, playerPos.y + playerDimensions.y / 1.5);

			case left:
				return new Vector2(playerPos.x + playerDimensions.x / 16, playerPos.y + playerDimensions.y / 2.8);

			case right:
				return new Vector2(playerPos.x + playerDimensions.x / 1.4, playerPos.y + playerDimensions.y / 1.8);

		default:
			return playerPos;
		}

	}

	public void update(double deltaTime, Vector2 playerPos, Vector2 playerDimensions, FacingDir facingDir) {

		this.playerPos = (playerPos);
		this.dir = getFacingDirAsVector(facingDir);
		this.facingDir = facingDir;

		if (mp == false) {
			ChangeWeapon();
			StatsBox.updateWeapon(getCurrentWeaponName());
		}

		this.weaponPos = getWeaponPos(playerPos, playerDimensions, dir);
		weapons.get(currentWeapon).fire(dir, weaponPos, false);

		for (int i = 0; i < allBullets.size(); i++) {

			allBullets.get(i).update(deltaTime);
		}
	}

	public void ChangeWeapon() {

		if (Input.isKeyPressed(KeyCode.DIGIT1)) {

			if (currentWeapon != 0) currentWeapon = 0;
			
		}

		else if (Input.isKeyPressed(KeyCode.DIGIT2)) {

			if (currentWeapon != 1) currentWeapon = 1;
		}

		else if (Input.isKeyPressed(KeyCode.DIGIT3)) {

			if (currentWeapon != 2) currentWeapon = 2;
		}
	}

	/**
	 *  Converts IEnumerable to Vector2
	 * @param facingDir
	 * @return Vector2 position
	 */
	public Vector2 getFacingDirAsVector(FacingDir facingDir) {

		switch (facingDir) {

		case up:
			return new Vector2(0, -1);

		case down:
			return new Vector2(0, 1);

		case left:
			return new Vector2(-1, 0);

		case right:
			return new Vector2(1, 0);

		default:
			return new Vector2();
		}
	}

	/**
	 * @return player position
	 */
	public Vector2 getPlayerPos() {
		return playerPos;
	}
	
	/**
	 * @return current weapon ID
	 */
	public int getCurrentWeaponNum() {
		return currentWeapon;
	}

	/**
	 * @return current weapon name
	 */
	public String getCurrentWeaponName() {
		return weapons.get(currentWeapon).getWeaponName() + ":" + weapons.get(currentWeapon).getAmmo();
	}

	/**
	 * Add a new bullet to the update list
	 * @param b - bullet to add
	 */
	public static void addBulletToList(Bullet b) {
		allBullets.add(b);
	}

	/**
	 *  Remove a bullet from the update list
	 * @param b - bullet to remove
	 */
	public static void removeBulletFromList(Bullet b) {
		allBullets.remove(b);
	}
	
	/**
	 * @param i - weapon ID
	 * @return weapon object
	 */
	public Weapon getWeaponFromList(int i) {
		return weapons.get(i);
	}

	/**
	 * @return current weapon object
	 */
	public Weapon getCurrentWeapon() {
		return weapons.get(currentWeapon);
	}
	
	/**
	 * Set current weapon via ID and update the UI
	 * @param wep - weapon ID to set
	 */
	public void setCurrentWeapon(int wep) {
		currentWeapon = wep;
		StatsBox.updateWeapon(getCurrentWeaponName());
	}

	/**
	 * Update the weapon readout in the UI
	 */
	public void updateWeaponReadout(){
		StatsBox.updateWeapon(getCurrentWeaponName());
	}

}
