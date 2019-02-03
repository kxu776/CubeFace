package com.zaxxon.world.mobile;

import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;

public class Player extends MovableSprite{

	int width = 128;
	int height = 128;
	ImagePattern imgPat;
	
	Vector2 velocity = new Vector2();
	double speed = 5.0;
	
    public Player(ImagePattern imgPat) {
        
    	controllable = true;
    	this.imgPat = imgPat;
        
        init();
    }
    
    private void init() {
    	
    	this.setWidth(width);
        this.setHeight(height);
        this.setFill(imgPat);
    }
    
    public void update(double time) {
    	
    	movement();
		
		Vector2 toMove = new Vector2 (velocity.x * time, velocity.y * time);
		this.translate(toMove);
		
		collision();
    }
    
    private void movement() {
    	
    	Vector2 inputDir = new Vector2();
    	
    	//X
    	
    	if (Input.isKeyPressed(KeyCode.LEFT) && Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		inputDir.x = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.LEFT)) {
			
    		inputDir.x = -1;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		inputDir.x = 1;
		}
    	
    	else inputDir.x = 0;
    	
    	//Y
    	
    	if (Input.isKeyPressed(KeyCode.DOWN) && Input.isKeyPressed(KeyCode.UP)) {
			
    		inputDir.y = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.DOWN)) {
			
    		inputDir.y = 1;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.UP)) {
			
    		inputDir.y = -1;
		}
    	
    	else inputDir.y = 0;
    	
    	
    	inputDir = Vector2.normalise(inputDir);
    	velocity = new Vector2 (inputDir.x * speed, inputDir.y * speed);
    }
    
    private void collision() {
    	
    	
    }
    
    
}
