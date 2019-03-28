package com.zaxxon.world.mobile;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.popups.GameOverPopup;
import com.zaxxon.ui.tools.StatsBox;
import com.zaxxon.world.shooting.WeaponManager;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class to contain all behaviour relating to the player character
 */
public class Player extends MovableSprite {

	public WeaponManager weaponManager;

	FacingDir facingDir;

	int width = 64;
	int height = 64;

	double deltaTime;
	public boolean end = false;
	public boolean mp = false;

	Vector2 inputDir = new Vector2();
	Vector2 moveDir = new Vector2();
	Vector2 velocity = new Vector2();
	final double maxSpeed = 5.0;
	final double acceleration = 1.2;
	final double deceleration = -0.6;
	double currentSpeed = 0;
	public int score = 0;

	private Boolean hit = false;
	private long damageTime = 0;
	private final long invincibilityTime = 400;
	private Lighting lighting;

	/**
	 * Default class constructor - spawns player at (500, 500)
	 */
	public Player() {

		controllable = true;
		this.setX(500);
		this.setY(800);
		init();
	}

	/**
	 * Class constructor - specifies spawn location of player
	 *
	 * @param spawnX x-coordinate of spawn location
	 * @param spawnY y-coordinate of spawn location
	 */
	public Player(double spawnX, double spawnY) {

		controllable = true;
		this.setX(spawnX);
		this.setY(spawnY);
		init();
	}

	/**
	 * Initialises relevant attribute values for instantiation
	 */
	private void init() {

		setImageSpriteSheet(SpriteImages.CUBEFACE_SPRITESHEET_IMAGE, 2, 4);
		setImageFromSpriteSheet(0);

		setWidth(width);
		setHeight(height);
		heal(100.0);
		isAlive = true;

		facingDir = FacingDir.up;
		weaponManager = new WeaponManager(this);

		lighting = new Lighting();
		lighting.setDiffuseConstant(2.0);
		lighting.setSpecularConstant(0.0);
		lighting.setSpecularExponent(0.0);
		lighting.setSurfaceScale(0.0);
		lighting.setLight(new Light.Distant(45, 45, Color.RED));
	}

	protected boolean oneTimeOnly = true;

	/**
	 * triggers all mechanisms within the player e.g. movement, damage, input etc
	 * 
	 * @param time         a delta time used for calculating truer values
	 * @param primaryStage the stage holding the game
	 */
	public void update(double time, Stage primaryStage) {
		if (!oneTimeOnly) {
			return;
		}
	
		if (!isAlive && !mp) {
			this.setOpacity(0.6);
			this.setEffect(lighting);
			oneTimeOnly = false;
			GameOverPopup.display(primaryStage, String.valueOf(score), MainMenu.mainmenu);
			return;
		}
		else if (end) {
			this.setOpacity(0.6);
			this.setEffect(lighting);
			oneTimeOnly = false;
			GameOverPopup.display(primaryStage, String.valueOf(score), MainMenu.mainmenu);
			return;
		}

		deltaTime = time;

		movement();

		Vector2 toMove = new Vector2(velocity.x * deltaTime, velocity.y * deltaTime);

		this.translate(toMove);
		collision(); // Checks for wall collision
		pickupsCollision();

		Vector2 playerPos = new Vector2(this.getX(), this.getY()); // Creates vector of player position

		weaponManager.update(deltaTime, playerPos, new Vector2(this.getWidth(), this.getHeight()), facingDir);

		// invincibility frames handling

		if (hit) {

			if (System.currentTimeMillis() - damageTime >= invincibilityTime) {

				setHit(false);
				this.setOpacity(1);
			}
		}

		StatsBox.updateScore(score); // Updates on-screen score display.

		draw();
		// System.out.println("X: " + getX() + " Y: " + getY() + "\n");
	}

	/**
	 * Calculate current speed using acceleration and deceleration basd on input,
	 * and turn it into a velocity vector
	 */
	private void movement() {

		inputDir = new Vector2();

		// ordering swaps to handle diagonal facing directions in the correct order

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

		velocity = new Vector2(moveDir.x * currentSpeed, moveDir.y * currentSpeed);
	}

	/**
	 * Get horizontal input and set facing direction
	 */
	private void moveX() {

		if (Input.isKeyPressed(KeyCode.A) && Input.isKeyPressed(KeyCode.D) && mp == false) {

			inputDir.x = 0;
		}

		else if (Input.isKeyPressed(KeyCode.A) && mp == false) {

			inputDir.x = -1;
			facingDir = FacingDir.left;

		}

		else if (Input.isKeyPressed(KeyCode.D) && mp == false) {

			inputDir.x = 1;
			facingDir = FacingDir.right;

		}

		else
			inputDir.x = 0;
	}

	/**
	 * Get vertical input and set facing direction
	 */
	private void moveY() {

		if (Input.isKeyPressed(KeyCode.S) && Input.isKeyPressed(KeyCode.W) && mp == false) {

			inputDir.y = 0;
		}

		else if (Input.isKeyPressed(KeyCode.S) && mp == false) {

			inputDir.y = 1;
			facingDir = FacingDir.down;
		}

		else if (Input.isKeyPressed(KeyCode.W) && mp == false) {

			inputDir.y = -1;
			facingDir = FacingDir.up;

		}

		else
			inputDir.y = 0;
	}

	/**
	 * Calculate collision and update with new pushed-out position
	 */
	private void collision() {

		Vector2 toMove = WallCollision.doCollision(this.getBoundsInLocal(), velocity);
		this.translate(toMove);
	}

	/**
	 * Checks if the player collides with a pickup object and sets variables based
	 * on type accordingly
	 */
	private void pickupsCollision() {

		for (int i = 0; i < MainGame.ammoPickupList.size(); i++) {

			if (this.getBoundsInLocal().intersects(MainGame.ammoPickupList.get(i).getBoundsInLocal())) {

				if (MainGame.ammoPickupList.get(i).type == 0) {

					// add MG ammo
					weaponManager.getWeaponFromList(1).addAmmo(200);
				} else if (MainGame.ammoPickupList.get(i).type == 1) {

					// add SG ammo
					weaponManager.getWeaponFromList(2).addAmmo(200);
				}

				else {

					this.heal(100);
					StatsBox.updateHealthBar((int) health);
				}

				MainGame.ammoPickupList.get(i).delete();
			}
		}
	}

	/**
	 * Updates the graphics image corresponding to facing direction
	 */
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

	/**
	 * @return facing direction
	 */
	public FacingDir getdir() {
		return facingDir;
	}

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}

	/**
	 * @return current held weapon ID
	 */
	public int getCurrentWeaponNum() {
		return weaponManager.getCurrentWeaponNum();
	}

	/**
	 * @return current held weapon name
	 */
	public String getCurrentWeaponName() {
		return weaponManager.getCurrentWeaponName();
	}

	/**
	 * Set ID direction as an IEnumerable
	 * 
	 * @param i
	 */
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

	/**
	 * @return IEnumerable direction as an ID
	 */
	public int getDir() {

		if (facingDir == FacingDir.up) {
			return 1;
		} else if (facingDir == FacingDir.down) {
			return 2;
		} else if (facingDir == FacingDir.left) {
			return 3;
		} else {
			return 4;
		}

	}

	/**
	 * @return player's width and height
	 */
	public Vector2 getplayerDimensions() {
		return new Vector2(this.getWidth(), this.getHeight());
	}

	/**
	 * Set the hit boolean and damage time
	 * 
	 * @param b - value to set
	 */
	public void setHit(Boolean b) {

		hit = b;

		if (b) {
			StatsBox.updateHealthBar((int) health);
			damageTime = System.currentTimeMillis();
			this.setOpacity(0.3);
		}

	}

	/**
	 * @return whether the player is in a state of damage
	 */
	public Boolean getHit() {
		return hit;
	}

	public void reset() {
		heal(100.0);
		isAlive = true;
		facingDir = FacingDir.down;
		// weaponManager = new WeaponManager(this);
	}

	/**
	 * @return velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void updateScore() {
		score +=1;
	}
	public void displayStats() {
		StatsBox.updateScore(score);
	}

}
