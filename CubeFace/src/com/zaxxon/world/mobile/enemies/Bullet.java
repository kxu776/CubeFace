package com.zaxxon.world.mobile.enemies;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;

//Written by Dan

import com.zaxxon.world.mobile.MovableSprite;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.ImagePattern;

public class Bullet extends MovableSprite {
	
	ImagePattern imgPat;
	
	private Vector2 direction;
	private double speed = 10;
	
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
		
		draw();
	}
	
	private void draw() {
		
		
	}
	
	private void getSpriteImage() {
		
		BufferedImage bimg = SpriteImages.BULLET_SPRITESHEET_IMAGE;
		imgPat = new ImagePattern(SwingFXUtils.toFXImage(bimg, null));
		
	}
}

