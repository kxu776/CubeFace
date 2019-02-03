package com.zaxxon.world.mobile;

import javafx.scene.paint.ImagePattern;

public class Player extends MovableSprite{

	int width = 128;
	int height = 128;
	ImagePattern imgPat;
	
    public Player(ImagePattern imgPat) {
        
    	controllable = true;
    	this.imgPat = imgPat;
        
        init();
    }
    
    void init() {
    	
    	this.setWidth(width);
        this.setHeight(height);
        this.setFill(imgPat);
    }
}
