package com.zaxxon.world.shooting;

import com.zaxxon.client.MainGame;
import com.zaxxon.gameart.SpriteImages;
import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Sprite;

public class AmmoPickup extends Sprite {

	public int type; //0 is MG, 1 is SG
	private long spawnedTime;
	private final long existTime = 1000*16;
	
	public AmmoPickup(int type, Vector2 pos) {
		
		this.setWidth(64);
		this.setHeight(32);
		this.setX(pos.x);
		this.setY(pos.y);
		
		spawnedTime = System.currentTimeMillis();
		this.type = type;
		
		
		if (type == 0) {
			
			setImage(SpriteImages.MGAP_IMAGE);
		}
		
		else {
			
			setImage(SpriteImages.SGAP_IMAGE);
		}
	}
	
	@Override
	public void delete() {
		
		MainGame.removeFromGame(this);
		MainGame.ammoPickupList.remove(this);
		
	}

	@Override
	public Boolean isAlive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update() {
		
		long elapsedTime = System.currentTimeMillis() - spawnedTime;
		
		if (elapsedTime >= existTime) {
			
			this.setOpacity(0.3);
				
			
			if (elapsedTime >= existTime*1.5f) {
				
				delete();
			}
		}
		
		
	}
	
	

}
