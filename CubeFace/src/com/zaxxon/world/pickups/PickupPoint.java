package com.zaxxon.world.pickups;
import com.zaxxon.maths.Vector2;

import java.awt.geom.Point2D;

/**
 * Represents a spawn location for item pickups on the map. Handles item respawns at said point.
 */

public class PickupPoint {

    private double X;   //X Coordinate on map
    private double Y;   // Y coordinate on map

    private final int COOLDOWN = 12000; //12 second respawn cooldown.
    private long spawnTime;             //Beginning of the respawn timer.
    public boolean spawned;             //Whether there is an pickup currently spawned at this point

    /**
     * Class constructor - defines map location of point based upon passed Point2D object.
     * @param coords Point2D object containing map coordinates for point initialization.
     */
    public PickupPoint(Point2D.Double coords){
        X = coords.x;
        Y = coords.y;
    }

    /**
     * Begins the respawn countdown for a pickup object on this point. Should be called when the object on this point is deleted/picked up.
     */
    public void startSpawnTimer(){
        spawned = false;
        spawnTime = System.currentTimeMillis();
    }

    /**
     * Arbitrates the respawn timer for a pickup item on this point.
     * If the cooldown has expired, true will be returned in order to initiate the spawning of a new item.
     *
     * @return true if this point's respawn cooldown has expired, otherwise false.
     */
    public boolean update() {
        if (!spawned) {
            if (System.currentTimeMillis() >= spawnTime + COOLDOWN) {
                spawned = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the positon of this point as a Vector2 object
     * @return X and Y coordinates of this PickupPoint
     */
    public Vector2 getPosVector(){
        return new Vector2(X, Y);
    }
}
