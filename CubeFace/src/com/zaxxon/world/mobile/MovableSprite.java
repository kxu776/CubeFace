package com.zaxxon.world.mobile;

import com.zaxxon.world.Sprite;

public class MovableSprite extends Sprite {
    private double velocityX;
    private double velocityY;
    private double movementSpeed;
    private double health;
    private double damage;
    public boolean controllable;

    public MovableSprite(){
        velocityX = 0.0;
        velocityY = 0.0;
        movementSpeed = 0.0;
        health = 0.0;
        damage = 0.0;
        positionX = 0;
        positionY = 0;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        this.positionX += velocityX * time;
        this.positionY += velocityY * time;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void heal(int healing){
        health += healing;
    }

    private double getHealth(){
        return health;
    }

    public void moveLeft(){
        addVelocity(-50,0);
    }

    public void moveUp(){
        addVelocity(0,50);
    }

    public void moveRight(){
        addVelocity(50,0);
    }

    public void moveDown(){
        addVelocity(0,-50);
    }

    public String toString(){
        return super.toString() + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

}
