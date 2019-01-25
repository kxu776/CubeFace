package com.zaxxon.client;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import com.zaxxon.input.Input;


public class ClientMain implements Runnable {

	private int width = 1280;
	private int height = 720;
	
	private Thread thread;
	private boolean running = false;
	
	private long window;
	
	
	public static void main(String[] args) {
		
		new ClientMain().start();
	}

	public void start() {
		
		running = true;
		thread = new Thread(this, "Client");
		thread.start();
	}

	private void initialise() {
		
		if (!glfwInit()) {
			
			//error
		}
		
		glfwWindowHint (GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "CubeFace", NULL, NULL);
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
	}
	
	
	public void run() {
		
		initialise();
		
		
		while (running) {
			
			//put some deltaTime stuff here to stabilise update rate
			
				update();
				render();
			
			if (glfwWindowShouldClose(window)) {
				
				running = false;
			}
		}
		
		
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void update() {
		
		glfwPollEvents();
	}
	
	private void render() {
		
	}
}

