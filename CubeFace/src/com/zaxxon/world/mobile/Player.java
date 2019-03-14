package com.zaxxon.world.mobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.MovableSprite.FacingDir;
import com.zaxxon.world.shooting.WeaponManager;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;


//Written by Dan

public class Player extends MovableSprite{
	
	WeaponManager weaponManager;
	
	FacingDir facingDir; 
	
	int width = 64;
	int height = 64;
	
	double deltaTime;
	
	Vector2 inputDir = new Vector2();
	Vector2 moveDir = new Vector2();
	Vector2 velocity = new Vector2();
	final double maxSpeed = 5.0;
	final double acceleration = 1.2;
	final double deceleration = -0.6;
	double currentSpeed = 0;
	
    public Player() {
        
    	controllable = true;
        
        init();
    }
    
    private void init() {
    	
    	setImageSpriteSheet(SpriteImages.CUBEFACE_SPRITESHEET_IMAGE, 2, 4);
    	setImageFromSpriteSheet(0);
    	
    	setWidth(width);
        setHeight(height);
        heal(100.0);
        isAlive = true;
        
        facingDir = FacingDir.up;
        this.setX(500);
        this.setY(800);
        weaponManager = new WeaponManager();
    }
    
    public void update(double time) {
    	
    	deltaTime = time;
    	
    	movement();
		
		Vector2 toMove = new Vector2 (velocity.x * deltaTime, velocity.y * deltaTime);
		this.translate(toMove);
		
		collision();
		
		Vector2 playerPos = new Vector2 (this.getX(), this.getY());

		//System.out.println("\nposX: " + Double.valueOf(this.getX()) + " posY: " + Double.valueOf(this.getY()));
		
		
		weaponManager.update(deltaTime, playerPos, new Vector2 (this.getWidth(), this.getHeight()), facingDir);
		
		draw();
    }
    
   
    
    private void movement() {
    	
    	inputDir = new Vector2();
    	
    	
    	//ordering swaps to handle diagonal facing directions in the correct order
    	
    	if (facingDir == FacingDir.up || facingDir == FacingDir.down) {
    		
    		moveX();
        	moveY();
    	}
    	
    	else {
    		
    		moveY();
    		moveX();
    	}
    	
    	inputDir = Vector2.normalise(inputDir);
    	
    	
    	if (Vector2.getMagnitude(inputDir) > 0) {
    		
    		currentSpeed = Math.min(maxSpeed, currentSpeed + acceleration * deltaTime);
    		moveDir = inputDir;
    	}
    	
    	else {
    		
    		currentSpeed = Math.max(0, currentSpeed + deceleration * deltaTime);
    	}
    	
    	velocity = new Vector2 (moveDir.x * currentSpeed, moveDir.y * currentSpeed);
    }
    
    private void moveX() {
    	
    	if (Input.isKeyPressed(KeyCode.A) && Input.isKeyPressed(KeyCode.D)) {
			
    		inputDir.x = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.A)) {
			
    		inputDir.x = -1;
    		facingDir = FacingDir.left;
    		
		}
    	
    	else if (Input.isKeyPressed(KeyCode.D)) {
			
    		inputDir.x = 1;
    		facingDir = FacingDir.right;
  
		}
    	
    	else inputDir.x = 0;
    }
    
    private void moveY() {
    	
    	if (Input.isKeyPressed(KeyCode.S) && Input.isKeyPressed(KeyCode.W)) {
			
    		inputDir.y = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.S)) {
			
    		inputDir.y = 1;
    		facingDir = FacingDir.down;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.W)) {
			
    		inputDir.y = -1;
    		facingDir = FacingDir.up;
    		
		}
    	
    	else inputDir.y = 0;
    }
    
    private void collision() {
    	
    	Vector2 toMove = WallCollision.doCollision(this.getBoundsInLocal(), velocity);
    	this.translate(toMove);
    }
    
    
    public void draw() {
    	
    	switch (facingDir) {
    	
    	case up: 
    		setImageFromSpriteSheet(0);
    		return;
    		
    	case down:
    		setImageFromSpriteSheet(4);
    		return;
    		
    	case left:
    		setImageFromSpriteSheet(6);
    		return;
    		
    	case right:
    		setImageFromSpriteSheet(2);
    		return;
    		
    	default:
    		//error
    	}
    }

    public FacingDir getdir() {
		return facingDir;
    }

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}	
	
	public String getCurrentWeaponName () {
		
		return weaponManager.getCurrentWeaponName();
	}
    
    
}
