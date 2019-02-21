package com.zaxxon.world.mobile.enemies;

import com.zaxxon.client.MainGame;
import com.zaxxon.maths.Vector2;

//Written by Dan

import com.zaxxon.world.mobile.MovableSprite;

public class Bullet extends MovableSprite {
	
	private Vector2 direction;
	private double speed;
	
	public Bullet (Vector2 dir, Vector2 pos) {
		
		MainGame.addSpriteToForeground(this);
		
		this.setX(pos.x);
		this.setY(pos.y);
		this.direction = dir;
	}
	
	public void update(double deltaTime) {
		
		Vector2 toMove = new Vector2();
		toMove = new Vector2 (direction.x * speed * deltaTime, direction.y * speed * deltaTime);
		this.translate(toMove);
		
		draw();
	}
	
	private void draw() {
		
		
	}
}
