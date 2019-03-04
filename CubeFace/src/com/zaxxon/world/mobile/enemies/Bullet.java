package com.zaxxon.world.mobile.enemies;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;

//Written by Dan

import com.zaxxon.world.mobile.MovableSprite;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import sun.applet.Main;

public class Bullet extends MovableSprite {
	
	ImagePattern imgPat;
	
	private Vector2 direction;
	private double speed = 10.0;
	private double damage = 10;
	
	public Bullet (Vector2 dir, Vector2 pos) {
		
		MainGame.addSpriteToForeground(this);
		
		this.setX(pos.x);
		this.setY(pos.y);
		this.direction = dir;
		
		getSpriteImage();
		this.setFill(imgPat);
		this.setWidth(8);
		this.setHeight(8);
	}
	
	public void update(double deltaTime) {
		
		Vector2 toMove = new Vector2();
		toMove = new Vector2 (direction.x * speed * deltaTime, direction.y * speed * deltaTime);
		this.translate(toMove);
		
		collision();
	}
	
	private void collision() {
		ArrayList<Pair<Integer, Bounds>> walls = Wall.getAllWallBoundsWithType();
		for(int i = 0;i < walls.size(); i++) {
			if(walls.get(i).getValue().intersects(this.getBoundsInParent())) {
				speed = 0;
				MainGame.removeSprite(this);
				return;
			}
		}
		for(Enemy enemy: MainGame.enemiesList){
			if(getBoundsInLocal().intersects(enemy.getBoundsInLocal())){
				enemy.takeDamage(damage);
				System.out.println("Health = " + String.valueOf(enemy.getHealth()));
				speed = 0;
				MainGame.removeSprite(this);
				return;
			}
		}
		/*Iterator<Enemy> enemyIter = MainGame.enemiesList.iterator();
		while(enemyIter.hasNext()){
			Enemy enemy = enemyIter.next();
			if(getBoundsInLocal().intersects(enemy.getBoundsInLocal())){
				enemy.takeDamage(damage);
				System.out.println("Health = " + String.valueOf(enemy.getHealth()));
				speed = 0;
				MainGame.removeSprite(this);
				return;
			}
		}
		*/

	}
	
	private void getSpriteImage() {
		
		BufferedImage bimg = SpriteImages.BULLET_SPRITESHEET_IMAGE;
		imgPat = new ImagePattern(SwingFXUtils.toFXImage(bimg, null));
		
	}
}

