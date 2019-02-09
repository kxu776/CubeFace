package com.zaxxon.world.mobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;

public class Player extends MovableSprite{

	enum FacingDir {
		
		up, down, left, right
	}
	
	FacingDir facingDir; 
	
	int width = 64;
	int height = 64;
	
	private BufferedImage[] sprites;
	private ImagePattern[] imgPats;
	
	Vector2 inputDir = new Vector2();
	Vector2 velocity = new Vector2();
	double speed = 5.0;
	
    public Player() {
        
    	controllable = true;
        
        init();
    }
    
    private void init() {
    	
    	getSpriteImages();
    	
    	this.setWidth(width);
        this.setHeight(height);
        
        facingDir = FacingDir.up;
    }
    
    public void update(double time) {
    	
    	movement();
		
		Vector2 toMove = new Vector2 (velocity.x * time, velocity.y * time);
		this.translate(toMove);
		
		collision();
		draw();
    }
    
    private void movement() {
    	
    	inputDir = new Vector2();
    	
    	if (facingDir == FacingDir.up || facingDir == FacingDir.down) {
    		
    		moveX();
        	moveY();
    	}
    	
    	else {
    		
    		moveY();
    		moveX();
    	}
    	
    	inputDir = Vector2.normalise(inputDir);
    	velocity = new Vector2 (inputDir.x * speed, inputDir.y * speed);
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
