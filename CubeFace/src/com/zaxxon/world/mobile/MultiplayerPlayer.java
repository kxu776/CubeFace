package com.zaxxon.world.mobile;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.shooting.WeaponManager;

public class MultiplayerPlayer extends MovableSprite {
	public WeaponManager weapon;

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

	public MultiplayerPlayer() {

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
		weapon = new WeaponManager();
	}

	public void update(double time) {

		deltaTime = time;

		Vector2 toMove = new Vector2(velocity.x * deltaTime, velocity.y * deltaTime);
		this.translate(toMove);

		collision();

		Vector2 playerPos = new Vector2(this.getX(), this.getY());

		weapon.update(deltaTime, playerPos, new Vector2(this.getWidth(), this.getHeight()), facingDir);

		draw();
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
			// error
		}
	}

	public FacingDir getdir() {
		return facingDir;
	}

	public void setDir(int i) {
		if (i == 1) {
			facingDir = FacingDir.up;
		} else if (i == 2) {
			facingDir = FacingDir.down;

		} else if (i == 3) {
			facingDir = FacingDir.left;

		} else if (i == 4) {
			facingDir = FacingDir.right;

		}
	}

	public Vector2 getplayerDimensions() {
		return new Vector2(this.getWidth(), this.getHeight());
	}

	
	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}

}
