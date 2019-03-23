package com.zaxxon.world.mobile.enemies;

import com.zaxxon.maths.Vector2;
import javafx.geometry.Bounds;

//Written by Dan

public class EnemyPlayerCollision {

	public static Vector2 DoCollision (Bounds enemy, Bounds player, Vector2 velocity) {
		
		double xDist = 0;
		double yDist = 0;
		
		//calculate how far into the wall the player has gone FOR EACH HIT WALL (because there are joins in wall boxes)
		//push out in the opposite direction corresponding to opposite velocity direction across all walls
		
		Boolean colRight = enemy.getMaxX() > player.getMinX() && enemy.getMaxX() < player.getMaxX();
		Boolean colLeft = enemy.getMinX() < player.getMaxX() && enemy.getMinX() > player.getMinX();
		Boolean colUp = enemy.getMaxY() > player.getMinY() && enemy.getMaxY() < player.getMaxY();
		Boolean colDown = enemy.getMinY() < player.getMaxY() && enemy.getMinY() > player.getMinY();
		
		if (!(colRight && colLeft)) {
			
			if (colRight) {
				
				xDist = -(enemy.getMaxX() - player.getMinX());
			}
			
			if (colLeft) {
				
				xDist = player.getMaxX() - enemy.getMinX();
			}
		}
		
		if (!(colUp && colDown)) {
			
			if (colUp) {
				
				yDist = -(enemy.getMaxY() - player.getMinY());
			}
			
			if (colDown) {
				
				yDist = player.getMaxY() - enemy.getMinY();
			}
		}
		
		Vector2 pushBox = new Vector2(xDist, yDist);
		Vector2 toMove = new Vector2();
		
		
		if (velocity.x != 0 && velocity.y != 0) { //diagonal movement
    		
    		if (pushBox.x != 0 && pushBox.y != 0) { //ie it must be a corner
    			
    			if (Math.abs(pushBox.x) == Math.abs(pushBox.y)) { //same distance inside in both directions
        			
        			toMove = new Vector2(pushBox.x, pushBox.y); //move both out
        		}
        		
        		else if (Math.abs(pushBox.x) > Math.abs(pushBox.y)) { //further inside in x than in y
        			
        			toMove = new Vector2(0, pushBox.y); //move out in y
        		}
    			
        		else if (Math.abs(pushBox.y) > Math.abs(pushBox.x)) { //further inside in y than in x
        			
        			toMove = new Vector2(pushBox.x, 0); //move out in x
        		}
    			
    			//all possibilities explicitly covered
    		}
    		
    		else { //one direction is 0 (fully inside) so just move both anyway since the undesired direction is already 0
    			
    			toMove = new Vector2(pushBox.x, pushBox.y);
    		}
    	}
    	
    	else if (velocity.x != 0) { //moving x only
    		
        	toMove = new Vector2(pushBox.x, toMove.y);
    	}
    	
    	else if (velocity.y != 0) { //moving y only
    		
        	toMove = new Vector2(toMove.x, pushBox.y);
    	}
		
		return toMove;
	}
	
	
	
	
	
	
}
