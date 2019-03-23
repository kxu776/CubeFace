package com.zaxxon.world.mobile;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.input.Input;
import com.zaxxon.maths.Vector2;
import com.zaxxon.ui.tools.StatsBox;
import com.zaxxon.world.shooting.WeaponManager;
import javafx.scene.input.KeyCode;


//Written by Dan

/**
 * Represents the player controlled sprite in this game.
 */
public class Player extends MovableSprite{
	
	public WeaponManager weaponManager;
	
	FacingDir facingDir; 
	
	int width = 64;
	int height = 64;
	
	double deltaTime;
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
    }
    
    public void update(double time) {
    	
    	deltaTime = time;
    	
    	movement();
		
		Vector2 toMove = new Vector2 (velocity.x * deltaTime, velocity.y * deltaTime);

		this.translate(toMove);
		
		collision();		//Checks for wall collision
		
		Vector2 playerPos = new Vector2 (this.getX(), this.getY()); 	//Creates vector of player position

		weaponManager.update(deltaTime, playerPos, new Vector2 (this.getWidth(), this.getHeight()), facingDir);
		
		
		//invincibility frames handling
		
		if (hit) {
			
			if (System.currentTimeMillis() - damageTime >= invincibilityTime) {
				
				setHit(false);
				this.setOpacity(1);
			}
		}

		StatsBox.updateScore(score);	//Updates on-screen score display.

		draw();
	}
    
   
    
    private void movement() {
    	
    	inputDir = new Vector2();
    	
    	
    	//ordering swaps to handle diagonal facing directions in the correct order
    	
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
    	
    	velocity = new Vector2 (moveDir.x * currentSpeed, moveDir.y * currentSpeed);
    }
    
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
    	
    	else inputDir.x = 0;
    }
    
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
    	
    	else inputDir.y = 0;
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
    		//error
    	}
    }

    public FacingDir getdir() {
		return facingDir;
    }

	@Override
	public void delete() {
		MainGame.removeFromGame(this);
	}	
	
	
	public int getCurrentWeaponNum() {
		return weaponManager.getCurrentWeaponNum();
	}
	public String getCurrentWeaponName () {
		return weaponManager.getCurrentWeaponName();
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
	
	public void setHit (Boolean b) {
		
		hit = b;
		
		if (b) {
		
			damageTime = System.currentTimeMillis();
			this.setOpacity(0.3);
		}
		
		
	}
    
	public Boolean getHit () {
		
		return hit;
	}
	
	public Vector2 getVelocity() {
		
		return velocity;
	}
    
}
