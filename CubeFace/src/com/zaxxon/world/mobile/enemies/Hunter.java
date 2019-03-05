package com.zaxxon.world.mobile.enemies;

import com.zaxxon.gameart.SpriteImages;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Hunter extends Enemy {

    public Hunter(double spawnX, double spawnY){
        controllable = false;
        this.setX(spawnX);
        this.setY(spawnY);
        setImageSpriteSheet(SpriteImages.HUNTER_ZOMBIE_SPRITESHEET_IMAGE, 2, 4);
        setImageFromSpriteSheet(0);
        this.setWidth(width);
        this.setHeight(height);
        facingDir = Enemy.FacingDir.up;
        isAlive = true;
        pathfinding = false;
    }

	@Override
	protected void attack() {
		// TODO Auto-generated method stub
		
	}

}
