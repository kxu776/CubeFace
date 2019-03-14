package com.zaxxon.world.shooting;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.enemies.Enemy;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;


//Written by Dan

public class Bullet extends MovableSprite {
	
	private Vector2 startPos;
	private Vector2 direction;
	private double speed = 10.0;
	private double damage = 10;

	private double despawnDistance;
	
	public Bullet (Vector2 dir, Vector2 pos, double damage, double dsd) {

		
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
	}
	
	public void update(double deltaTime) {

		checkDespawn();
		
		Vector2 toMove = new Vector2();
		toMove = new Vector2 (direction.x * speed * deltaTime, direction.y * speed * deltaTime);
		this.translate(toMove);
		
		collision();

	}
	
	private void checkDespawn() {
		
		double distanceTravelled = Math.sqrt(Math.pow(this.getX()-startPos.x, 2) + Math.pow(this.getY()-startPos.y, 2));
		
		if (distanceTravelled >= despawnDistance) {
			
			delete();
		}
	}
	
	private void collision() {
		//System.out.println("Yes");

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
				enemy.takeDamage(damage);
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



