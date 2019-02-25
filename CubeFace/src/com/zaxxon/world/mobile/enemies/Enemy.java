package com.zaxxon.world.mobile.enemies;

import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.mobile.MovableSprite;
import com.zaxxon.world.mobile.Player;
import com.zaxxon.world.mobile.WallCollision;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static java.lang.Math.abs;

public class Enemy extends MovableSprite {

    FacingDir facingDir;

    int width = 64;
    int height = 64;

    private BufferedImage[] sprites;
    private ImagePattern[] imgPats;

    protected static double pX, pY;

    double deltaTime;

    Vector2 inputDir = new Vector2();
    Vector2 moveDir = new Vector2();
    Vector2 velocity = new Vector2();
    final double maxSpeed = 1.5;
    final double acceleration = 1.2;
    final double deceleration = -0.6;
    double currentSpeed = 0;
    private double damage = 0.1;

    public Enemy(double spawnX, double spawnY) {
        controllable = false;
        this.setX(spawnX);
        this.setY(spawnY);

        init();
    }

    private void init() {
        getSpriteImages();
        this.setWidth(width);
        this.setHeight(height);
        facingDir = Enemy.FacingDir.up;
        isAlive = true;
    }

    public void update(double time, Player player) {
        pX = player.getX();
        pY = player.getY();
        damage(player);
        //collision();
        deltaTime = time;
        movement(pX, pY);
        Vector2 toMove = new Vector2(velocity.x * deltaTime, velocity.y * deltaTime);
        collision();
        this.translate(toMove);

        collision();
        draw();
    }

    private void movement(double pX, double pY) {

        inputDir = new Vector2();
        //ordering swaps to handle diagonal facing directions in the correct order
        if (abs(pX-this.getX())>abs(pY-this.getY())) {
            moveY();
            moveX();
        } else {
            moveX();
            moveY();
        
        }
        inputDir = Vector2.normalise(inputDir);

        if (Vector2.getMagnitude(inputDir) > 0) {
            currentSpeed = Math.min(maxSpeed, currentSpeed + acceleration * deltaTime);
            moveDir = inputDir;
        } else {
            currentSpeed = Math.max(0, currentSpeed + deceleration * deltaTime);
        }
        velocity = new Vector2(moveDir.x * currentSpeed, moveDir.y * currentSpeed);
    }

    private void moveX() {
        if (this.getX()<pX) {  //enemy is to the left of the player
            inputDir.x = 1;
            facingDir = Enemy.FacingDir.right;
        } else if (this.getX()>pX) {    //enemy is to the right of the player
            inputDir.x = -1;
            facingDir = Enemy.FacingDir.left;
        } else inputDir.x = 0;      //enemy is horizontally inline with the player.
    }

    private void moveY() {
        if (this.getY()<pY) {  //enemy is above the player
            inputDir.y = 1;
            facingDir = Enemy.FacingDir.down;
        } else if (this.getY()>pY) {    //enemy is below the player
            inputDir.y = -1;
            facingDir = Enemy.FacingDir.up;
        } else inputDir.y = 0;      //enemy is vertically inline with the player.
    }


    private void collision() {
        Vector2 toMove = WallCollision.doCollision(this.getBoundsInLocal(), velocity);
        this.translate(toMove);
    }

    //Inflicts damage to player if collision occurs
    private void damage(Player player){
        if(this.getBoundsInLocal().intersects(player.getX(),player.getY(),player.getWidth(),player.getHeight())){   //collision check
            player.takeDamage(this.damage);
            System.out.println("Health: " + String.valueOf(player.getHealth()));
        }
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

    private void attack() {
        //TODO: implement damage system.
    }


    private void getSpriteImages() {
        BufferedImage enemySS = null;

        try {
            File f = new File(SpriteImages.ZOMBIE_SPRITESHEET_URL);
            enemySS = ImageIO.read(f);
        } catch (IOException e) {
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

                sprites[(i * cols) + j] = enemySS.getSubimage(j * width, i * height, width, height);
            }
        }

        imgPats = new ImagePattern[sprites.length];

        for (int i = 0; i < sprites.length; i++) {

            Image img = SwingFXUtils.toFXImage(sprites[i], null);
            imgPats[i] = new ImagePattern(img);
        }
    }

    @Override
    public LinkedHashMap<String, Object> getAttributes() {
        LinkedHashMap<String,Object> attributes =  super.getAttributes();
        attributes.put("Damage", damage);
        return attributes;
    }
}