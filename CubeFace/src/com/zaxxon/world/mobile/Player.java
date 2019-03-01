package com.zaxxon.world.mobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;
import com.zaxxon.world.mobile.enemies.Weapon;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;


//Written by Dan

public class Player extends MovableSprite{
	
	Weapon weapon;
	
	FacingDir facingDir; 
	
	int width = 64;
	int height = 64;
	
	private BufferedImage[] sprites;
	private ImagePattern[] imgPats;
	
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
    	
    	getSpriteImages();
    	
    	setWidth(width);
        setHeight(height);
        heal(100.0);
        isAlive = true;
        
        facingDir = FacingDir.up;
        this.setX(500);
        this.setY(800);
        weapon = new Weapon();
    }
    
    public void update(double time) {
    	
    	deltaTime = time;
    	
    	movement();
		
		Vector2 toMove = new Vector2 (velocity.x * deltaTime, velocity.y * deltaTime);
		this.translate(toMove);
		
		collision();
		
		Vector2 playerPos = new Vector2 (this.getX(), this.getY());

		//System.out.println("\nposX: " + Double.valueOf(this.getX()) + " posY: " + Double.valueOf(this.getY()));
		
		
		weapon.update(deltaTime, playerPos, new Vector2 (this.getWidth(), this.getHeight()), facingDir);
		
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
    	
    	if (Input.isKeyPressed(KeyCode.LEFT) && Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		inputDir.x = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.LEFT)) {
			
    		inputDir.x = -1;
    		facingDir = FacingDir.left;
    		
		}
    	
    	else if (Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		inputDir.x = 1;
    		facingDir = FacingDir.right;
  
		}
    	
    	else inputDir.x = 0;
    }
    
    private void moveY() {
    	
    	if (Input.isKeyPressed(KeyCode.DOWN) && Input.isKeyPressed(KeyCode.UP)) {
			
    		inputDir.y = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.DOWN)) {
			
    		inputDir.y = 1;
    		facingDir = FacingDir.down;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.UP)) {
			
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
    		this.setFill(imgPats[0]);
    		return;
    		
    	case down:
    		this.setFill(imgPats[4]);
    		return;
    		
    	case left:
    		this.setFill(imgPats[6]);
    		return;
    		
    	case right:
    		this.setFill(imgPats[2]);
    		return;
    		
    	default:
    		//error
    	}
    }

    
    private void getSpriteImages() {
    	
    	BufferedImage playerSS = null;
		
    	try {
			File f = new File(SpriteImages.CUBEFACE_SPRITESHEET_URL);
			playerSS = ImageIO.read(f);
		} 
    	
    	catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
    	final int width = 128;
		final int height = 128;
		final int rows = 2;
		final int cols = 4;
		sprites = new BufferedImage[rows * cols];

		for (int i = 0; i < rows; i++) {
			
			for (int j = 0; j < cols; j++) {
				
				sprites[(i * cols) + j] = playerSS.getSubimage(j * width, i * height, width, height);
			}
		}
		
		imgPats = new ImagePattern[sprites.length];
		
		for (int i = 0; i < sprites.length; i++) {
			
			Image img = SwingFXUtils.toFXImage(sprites[i], null);
			imgPats[i] = new ImagePattern(img);
		}

		
    }
    
    
}
