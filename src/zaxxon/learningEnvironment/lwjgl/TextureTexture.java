//package zaxxon.learningEnvironment.lwjgl;
//
//import java.awt.*;
//import java.awt.image.*;
//import java.io.*;
//import java.nio.*;
//
//import org.lwjgl.*;
//import org.lwjgl.glfw.*;
//import org.lwjgl.opengl.*;
//import org.lwjgl.system.MemoryUtil;
//import org.newdawn.slick.opengl.*;
//import org.newdawn.slick.util.*;
//
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryUtil.NULL;
//import static org.lwjgl.glfw.GLFW.*;
//
//public class TextureTexture {
//
//	private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
//
//	private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
//		@Override
//		public void invoke(long window, int key, int scancode, int action, int mods) {
//			if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
//				glfwSetWindowShouldClose(window, true);
//			}
//		}
//	};
//
//	public static void main(String[] args) {
//		glfwSetErrorCallback(errorCallback);
//		if (!glfwInit()) {
//			throw new IllegalStateException("Unable to initialize GLFW");
//		}
//
//		long window = glfwCreateWindow(640, 480, "Simple example", NULL, NULL);
//		if (window == NULL) {
//			glfwTerminate();
//			throw new RuntimeException("Failed to create the GLFW window");
//		}
//
//		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		glfwSetWindowPos(window, (vidMode.width() - 640) / 2, (vidMode.height() - 480) / 2);
//
//		glfwMakeContextCurrent(window);
//		GL.createCapabilities();
//
//		glfwSwapInterval(1);
//
//		glfwSetKeyCallback(window, keyCallback);
//
//		glEnable(GL_TEXTURE_2D);
//
//		Texture texture;
//		try {
//			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/zaxxon/learningEnvironment/lwjgl/puppy.png"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(1);
//			return;
//		}
//
//		IntBuffer width = MemoryUtil.memAllocInt(1);
//		IntBuffer height = MemoryUtil.memAllocInt(1);
//
//		while (!glfwWindowShouldClose(window)) {
//			glfwGetFramebufferSize(window, width, height);
//			float ratio = width.get() / (float) height.get();
//			ratio *= 10f;
//
//			width.rewind();
//			height.rewind();
//
//			glViewport(0, 0, width.get(), height.get());
//			glClear(GL_COLOR_BUFFER_BIT);
//
//
//			glMatrixMode(GL_PROJECTION);
//			glLoadIdentity();
//			glOrtho(-ratio, ratio, -10f, 10f, 1f, -1f);
//			glMatrixMode(GL_MODELVIEW);
//
//			glLoadIdentity();
//
//			//
//			texture.bind();
//			glBegin(GL_TRIANGLES);
//			glTexCoord2f(1, 0);
//			glVertex2i(450, 10);
//			glTexCoord2f(0, 0);
//			glVertex2i(10, 10);
//			glTexCoord2f(0, 1);
//			glVertex2i(10, 450);
//			glTexCoord2f(0, 1);
//			glVertex2i(10, 450);
//			glTexCoord2f(1, 1);
//			glVertex2i(450, 450);
//			glTexCoord2f(1, 0);
//			glVertex2i(450, 10);
//			glEnd();
//			//
//
//			glfwSwapBuffers(window);
//			glfwPollEvents();
//
//			width.flip();
//			height.flip();
//		}
//
//		MemoryUtil.memFree(width);
//		MemoryUtil.memFree(height);
//
//		glfwDestroyWindow(window);
//		keyCallback.free();
//
//		glfwTerminate();
//		errorCallback.free();
//	}
//
//}
