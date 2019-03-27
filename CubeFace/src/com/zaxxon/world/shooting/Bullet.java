package com.zaxxon.world.shooting;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.enemies.Enemy;
import javafx.geometry.Bounds;
import javafx.util.Pair;

import java.util.ArrayList;


/**
 * @author Dan
 * 
 * Class to handle behaviour for bullets that have been shot
 */
public class Bullet extends MovableSprite {
	
	private Vector2 startPos;
	private Vector2 direction;
	private double speed = 10.0;
	private double damage = 10;
	public Player player;

	private double despawnDistance;
	
	/**
	 * Creates a new bullet and initialises it
	 * @param dir direction to move in
	 * @param pos position to spawn at
	 * @param damage own damage value
	 * @param dsd despawn distance
	 * @param player player that shot it
	 */
	public Bullet (Vector2 dir, Vector2 pos, double damage, double dsd, Player player) {
		MainGame.addSpriteToForeground(this);
		
		this.setX(pos.x);
		this.setY(pos.y);
		this.startPos = pos;
		this.direction = dir;
		this.damage = damage;
		this.despawnDistance = dsd;
		setImage(SpriteImages.BULLET_SPRITESHEET_IMAGE);
		this.setWidth(8);
		this.setHeight(8);
		this.player = player;
	}
	
	public void update(double deltaTime) {

		checkDespawn();
		
		Vector2 toMove = new Vector2();
		toMove = new Vector2 (direction.x * speed * deltaTime, direction.y * speed * deltaTime);
		this.translate(toMove);
		
		collision();

	}
	
	/**
	 * Despawns the bullet after the specified distance travelled
	 */
	private void checkDespawn() { 
		double distanceTravelled = Math.sqrt(Math.pow(this.getX()-startPos.x, 2) + Math.pow(this.getY()-startPos.y, 2));
		
		if (distanceTravelled >= despawnDistance) {
			
			delete();
		}
	}
	
	/**
	 * Checks for collision between walls, enemies, and players
	 * When hit, deletes the bullet and updates respectively in the collided object
	 */
	private void collision() {

		ArrayList<Pair<Integer, Bounds>> walls = Wall.getAllWallBoundsWithType();
		ArrayList<Enemy> enemies = new ArrayList<>(MainGame.enemiesList);

		for(int i = 0;i < walls.size(); i++) {
			if(walls.get(i).getValue().intersects(this.getBoundsInParent())) {
				speed = 0;
				delete();
				return;
			}
		}
		for(Enemy enemy : enemies){
			if(getBoundsInLocal().intersects(enemy.getBoundsInLocal())){
				enemy.takeDamage(damage, player);
				delete();
				return;
			}
		}
		
		for (Player p : MainGame.playerList) {
			
			if (p.equals(player)) {
				
				continue;
			}
			
			if(getBoundsInLocal().intersects(p.getBoundsInLocal())){
				p.takeDamage(damage, player);
				delete();
				return;
			}
		}

	}

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
		WeaponManager.removeBulletFromList(this);
	}
}



