package zaxxon.learningEnvironment.lwjgl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.*;

public class GettingStarted {

	private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);

	private static int xOffset = 0;
	private static int yOffset = 0;

	private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
				glfwSetWindowShouldClose(window, true);
			} else if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
				yOffset -= 100;
			} else if (key == GLFW_KEY_DOWN && action == GLFW_PRESS) {
				yOffset += 100;
			} else if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
				xOffset += 100;
			} else if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
				xOffset -= 100;
			}
		}
	};

	public static void main(String[] args) {
		glfwSetErrorCallback(errorCallback);
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		long window = glfwCreateWindow(640, 480, "Simple example", NULL, NULL);
		if (window == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create the GLFW window");
		}

		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - 640) / 2, (vidMode.height() - 480) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		glfwSwapInterval(1);

		glfwSetKeyCallback(window, keyCallback);

		IntBuffer width = MemoryUtil.memAllocInt(1);
		IntBuffer height = MemoryUtil.memAllocInt(1);

//		Texture walls = Texture.loadTexture("src/zaxxon/learningEnvironment/lwjgl/walls.jpg");
//		walls.bind();
		
//		int[] pixels = new int[image.getWidth() * image.getHeight()];
//        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
//
//        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
//        
//        for(int y = 0; y < image.getHeight(); y++){
//            for(int x = 0; x < image.getWidth(); x++){
//                int pixel = pixels[y * image.getWidth() + x];
//                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
//                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
//                buffer.put((byte) (pixel & 0xFF));               // Blue component
//                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
//            }
//        }
//
//        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
//
//        // You now have a ByteBuffer filled with the color data of each pixel.
//        // Now just create a texture ID and bind it. Then you can load it using 
//        // whatever OpenGL method you want, for example:
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8A, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		while (!glfwWindowShouldClose(window)) {
			glfwGetFramebufferSize(window, width, height);
			float ratio = width.get() / (float) height.get();
			ratio *= 10f;

			width.rewind();
			height.rewind();

			glViewport(xOffset, yOffset, width.get(), height.get());
			glClear(GL_COLOR_BUFFER_BIT);

			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(-ratio, ratio, -10f, 10f, 1f, -1f);
			glMatrixMode(GL_MODELVIEW);

			glLoadIdentity();
			glRotatef((float) glfwGetTime() * 50f, 0f, 0f, 1f);
//			glRotatef((float) glfwGetTime() * 50f, (float) glfwGetTime() * 60f, (float) glfwGetTime() * 40f, (float) glfwGetTime() * 70f);

//			glBegin(GL_TRIANGLES);
//			glColor3f(1f, 0f, 0f);
//			glVertex3f(-0.6f, -0.4f, 0f);
//			glColor3f(0f, 1f, 0f);
//			glVertex3f(0.6f, -0.4f, 0f);
//			glColor3f(0f, 0f, 1f);
//			glVertex3f(0f, 0.6f, 0f);
//			glEnd();
//
//			glBegin(GL_LINE_STRIP);
//			glColor3f(1f, 1f, 0f);
//			glVertex3f(-0.8f, 0.8f, 0f);
//			glColor3f(0f, 1f, 1f);
//			glVertex3f(0.8f, 0.8f, 0f);
//			glEnd();
//
//			glBegin(GL_QUADS);
//			glColor4f(1f, 0f, 0f, 0.5f);
//			glVertex2f(0.3f, 0.3f);
//			glColor4f(0f, 1f, 0f, 0.5f);
//			glVertex2f(-0.3f, 0.3f);
//			glColor4f(0f, 0f, 1f, 0.5f);
//			glVertex2f(-0.3f, -0.3f);
//			glColor4f(1f, 1f, 1f, 0.5f);
//			glVertex2f(0.3f, -0.3f);
//			glEnd();
//
//			glBegin(GL_QUADS);
//			glColor4f(0.2f, 0.3f, 0.4f, 0.5f);
//			glVertex2i(10, 1);
//			glColor4f(0.2f, 0.3f, 0.4f, 0.5f);
//			glVertex2i(10, 10);
//			glColor4f(0.2f, 0.3f, 0.4f, 0.5f);
//			glVertex2i(1, 10);
//			glColor4f(0.2f, 0.3f, 0.4f, 0.5f);
//			glVertex2i(1, 1);
//			glEnd();
			
//			drawTexture(walls, 0f, 0f);

			glfwSwapBuffers(window);
			glfwPollEvents();

			width.flip();
			height.flip();
		}

		MemoryUtil.memFree(width);
		MemoryUtil.memFree(height);

		glfwDestroyWindow(window);
		keyCallback.free();

		glfwTerminate();
		errorCallback.free();
	}

}
