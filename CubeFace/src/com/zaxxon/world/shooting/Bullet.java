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
	
	ImagePattern imgPat;
	
	private Vector2 direction;
	private double speed = 10.0;
	private double damage = 10;
	private boolean alive = false;
	
	public Bullet (Vector2 dir, Vector2 pos, double damage) {
		alive = true;
		
		MainGame.addSpriteToForeground(this);
		
		this.setX(pos.x);
		this.setY(pos.y);
		this.direction = dir;
		this.damage = damage;
		
		getSpriteImage();
		this.setFill(imgPat);
		this.setWidth(8);
		this.setHeight(8);
	}
	
	public void update(double deltaTime) {
		if(alive) {
			Vector2 toMove = new Vector2();
			toMove = new Vector2(direction.x * speed * deltaTime, direction.y * speed * deltaTime);
			this.translate(toMove);
			collision();
		}
	}

	private void collision() {
		//System.out.println("Yes");

		ArrayList<Pair<Integer, Bounds>> walls = Wall.getAllWallBoundsWithType();
		ArrayList<Enemy> enemies = new ArrayList<>(MainGame.enemiesList);

		for(int i = 0;i < walls.size(); i++) {
			if(walls.get(i).getValue().intersects(this.getBoundsInParent())) {
				speed = 0;
				MainGame.removeSprite(this);
				return;
			}
		}
		int index = 0;
		for(Enemy enemy : enemies){
			if(getBoundsInLocal().intersects(enemy.getBoundsInLocal())){
				MainGame.enemiesList.get(index).takeDamage(damage);
				System.out.println(MainGame.enemiesList.get(index).getHealth());
				speed = 0;
				damage = 0;
				alive = false;
				MainGame.removeSpriteFromForeground(this);
				setX(0.0);
				setY(0.0);
				index++;
				return;
			}
		}

	}

	private void getSpriteImage() {

		BufferedImage bimg = SpriteImages.BULLET_SPRITESHEET_IMAGE;
		imgPat = new ImagePattern(SwingFXUtils.toFXImage(bimg, null));

	}

	private void destroy(){
		MainGame.removeSpriteFromForeground(this);
	}
}



