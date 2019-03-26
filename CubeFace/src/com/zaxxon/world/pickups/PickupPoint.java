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

    public PickupPoint(Point2D.Double coords){
        X = coords.x;
        Y = coords.y;
    }

    public void startSpawnTimer(){
        spawned = false;
        spawnTime = System.currentTimeMillis();
    }

    public boolean update() {
        if (!spawned) {
            if (System.currentTimeMillis() >= spawnTime + COOLDOWN) {
                spawned = true;
                return true;
            }
        }
        return false;
    }

    public Vector2 getPosVector(){
        return new Vector2(X, Y);
    }
}
