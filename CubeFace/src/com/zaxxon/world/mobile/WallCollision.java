package com.zaxxon.world.mobile;

import java.util.ArrayList;

import com.zaxxon.maths.Vector2;
import com.zaxxon.world.Wall;

import javafx.geometry.Bounds;
import javafx.util.Pair;


/**
 * @author Dan
 *
 *  Class to handle resolving collisions between a moving object and a wall
 */
public class WallCollision {

	/** Firstly calculate the intersections with the walls 
	 *  Secondly push the object out to correct its position with the correct distance and direction
	 * @param movingObject 
	 * @param velocity - velocity of the moving object
	 * @return the position to update it with
	 */
	public static Vector2 doCollision(Bounds movingObject, Vector2 velocity) {
		
		ArrayList<Pair<Integer, Bounds>> walls = Wall.getAllWallBoundsWithType();
    	ArrayList<Pair<Integer, Vector2>> intersections = new ArrayList<Pair<Integer, Vector2>>();
    	Boolean insideCorner = false;
    	
    	for (int i = 0; i < walls.size(); i++) {
    		
    		if (walls.get(i).getValue().intersects(movingObject)) {
    			
    			Vector2 intersection = calcIntersection(walls.get(i).getValue(), movingObject);
    			intersections.add(new Pair<Integer, Vector2>(walls.get(i).getKey(), intersection));
    		}
    	}
    	
    	Vector2 pushBox = new Vector2();
    	
    	if (intersections.size() == 1) { //one wall
    		
    		pushBox = intersections.get(0).getValue();
    	}
    	
    	else if (intersections.size() == 2) { //two joining walls, possibly an inside corner
    		
    		Vector2 b0 = intersections.get(0).getValue();
    		Vector2 b1 = intersections.get(1).getValue();
    		
    		if (b0.x == b1.x) {
    			
    			pushBox = new Vector2 (b0.x, 0);
    		}
    		
    		else if (b0.y == b1.y) {
    			
    			pushBox = new Vector2 (0, b0.y);
    		}
    		
    		else {
    			
    			insideCorner = true;
    			
    			if (b0.x == 0 || b1.y == 0) {
    				
    				pushBox = new Vector2 (b1.x, b0.y);
    			}
    			
    			else if (b1.x == 0 || b0.y == 0) {
    				
    				pushBox = new Vector2 (b0.x, b1.y);
    			}
    			
    			else {
    				
    				if (Math.abs(b0.x) > Math.abs(b1.x)) {
    					
    					pushBox = new Vector2 (b1.x, b0.y);
    				}
    				
    				else if (Math.abs(b1.x) > Math.abs(b0.x)) {
    					
    					pushBox = new Vector2 (b0.x, b1.y);
    				}
    			}
    		}
    	}
    	
    	else if (intersections.size() == 3) { //an inside corner
    		
    		insideCorner = true;
    		
    		//one MUST have the id of a corner piece (all 3 hit intersections always involve one and only one corner piece)
    		//we want to find which one is the corner piece and discard it, using only the two sides to push outwards
    		
    		for (int i = 0; i < 3; i++) {
    			
    			if (intersections.get(i).getKey() == 1) {
    				
    				intersections.remove(i);
    				break;
    			}
    		}
    		
    		Vector2 b0 = intersections.get(0).getValue();
    		Vector2 b1 = intersections.get(1).getValue();
    		
    		//we need to find the correct orientation of the two walls to get the right push direction
    		
    		if (b0.x * velocity.x > 0) { //if in the same direction
    			
    			pushBox = new Vector2 (b1.x, b0.y); //get the other way round
    		}
    		
    		else if (b0.x * velocity.x < 0) { //same idea in reverse
    			
    			pushBox = new Vector2 (b0.x, b1.y);
    		}
    		
    	}
    	
    	
    	Vector2 toMove = new Vector2();
    	
    	if (insideCorner) {
    		
    		toMove = pushBox;
    	}
    	
    	else {
    		
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
        		
        		else { //one direction is 0 (fully inside a wall) so just move both anyway since the undesired direction is already 0
        			
        			toMove = new Vector2(pushBox.x, pushBox.y);
        		}
        	}
        	
        	else if (velocity.x != 0) { //moving x only
        		
            	toMove = new Vector2(pushBox.x, toMove.y);
        	}
        	
        	else if (velocity.y != 0) { //moving y only
        		
            	toMove = new Vector2(toMove.x, pushBox.y);
        	}
    	}
    	
    	return toMove;
	}
	
	/**
	 * calculates how far into a wall the object has penetrated
	 * 
	 * @param wall
	 * @param movingObject
	 * @return Vector2 for x and y penetration amounts
	 */
	private static Vector2 calcIntersection(Bounds wall, Bounds movingObject) {
	    	
		double wallBuffer = 0.5;
		Bounds pb = movingObject;
		
		double xDist = 0;
		double yDist = 0;
		
		//calculate how far into the wall the player has gone FOR EACH HIT WALL (because there are joins in wall boxes)
		//push out in the opposite direction corresponding to opposite velocity direction across all walls
		
		Boolean colRight = pb.getMaxX() > wall.getMinX() && pb.getMaxX() < wall.getMaxX();
		Boolean colLeft = pb.getMinX() < wall.getMaxX() && pb.getMinX() > wall.getMinX();
		Boolean colUp = pb.getMaxY() > wall.getMinY() && pb.getMaxY() < wall.getMaxY();
		Boolean colDown = pb.getMinY() < wall.getMaxY() && pb.getMinY() > wall.getMinY();
		
		if (!(colRight && colLeft)) {
			
			if (colRight) {
				
				xDist = -(pb.getMaxX() - wall.getMinX() + wallBuffer);
			}
			
			if (colLeft) {
				
				xDist = wall.getMaxX() - pb.getMinX() + wallBuffer;
			}
		}
		
		if (!(colUp && colDown)) {
			
			if (colUp) {
				
				yDist = -(pb.getMaxY() - wall.getMinY() + wallBuffer);
			}
			
			if (colDown) {
				
				yDist = wall.getMaxY() - pb.getMinY() + wallBuffer;
			}
		}
		
		return new Vector2(xDist, yDist);
	}
}
