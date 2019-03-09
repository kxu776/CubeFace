package com.zaxxon.world.shooting;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	
	public Bullet (Vector2 dir, Vector2 pos, double damage) {
		
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

		for(Enemy enemy : MainGame.enemiesList){
			if(getBoundsInLocal().intersects(enemy.getBoundsInLocal())){
				//TODO: Enemy takes damage
				speed = 0;
				MainGame.removeSprite(this);
				return;
			}
		}

	}

	private void getSpriteImage() {

		BufferedImage bimg = SpriteImages.BULLET_SPRITESHEET_IMAGE;
		imgPat = new ImagePattern(SwingFXUtils.toFXImage(bimg, null));

	}
}


