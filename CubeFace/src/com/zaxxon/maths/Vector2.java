package com.zaxxon.maths;

public class Vector2 {

	public double x, y;
	
	public Vector2() {
		
		x = y = 0;
	}
	
	public Vector2(double x, double y) {
		
		this.x = x;
		this.y = y;
	}
	
	public static double getMagnitude(Vector2 v) {
		
		return Math.sqrt(v.x * v.x + v.y * v.y);
	}
	
	public static Vector2 normalise(Vector2 v) {
		
		double m = getMagnitude (v);
		
		if (m == 0) {
			
			return v;
		}
		
		else {
			
			return new Vector2 (v.x / m, v.y / m);
		}
	}
	
}
