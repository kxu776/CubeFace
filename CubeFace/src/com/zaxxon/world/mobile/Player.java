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
    	
    	if (Input.isKeyPressed(KeyCode.LEFT) && Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		velocity.x = 0;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.LEFT)) {
			
    		velocity.x = -5;
		}
    	
    	else if (Input.isKeyPressed(KeyCode.RIGHT)) {
			
    		velocity.x = 5;
		}
    	
    	else velocity.x = 0;
		
		Vector2 toMove = new Vector2 (velocity.x * time, velocity.y * time);
		this.translate(toMove);
    }
    
    
}
