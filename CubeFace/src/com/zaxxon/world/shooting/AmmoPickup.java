package com.zaxxon.world.shooting;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;

public class AmmoPickup extends Sprite {

	public int type; //0 is MG, 1 is SG
	private long spawnedTime;
	
	public AmmoPickup(int type, Vector2 pos) {
		
		spawnedTime = System.currentTimeMillis();
		this.type = type;
		this.setX(pos.x);
		this.setY(pos.y);
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isAlive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	void update() {
		
		
	}
	
	

}
