package com.zaxxon.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {
	
	public static boolean[] keys = new boolean[100];
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		keys[key] = action != GLFW.GLFW_RELEASE;
	}

}
