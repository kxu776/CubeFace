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

    protected final String DEFAULT_SPRITESHEET = SpriteImages.HUNTER_ZOMBIE_SPRITESHEET_URL;


    public Hunter(double spawnX, double spawnY){
        super(spawnX,spawnY, SpriteImages.HUNTER_ZOMBIE_SPRITESHEET_URL);
    }

    /*private void init() {
        getSpriteImages();
        this.setWidth(super.width);
        this.setHeight(super.height);
        facingDir = Enemy.FacingDir.up;
        isAlive = true;
        pathfinding = false;
    }*/


}
