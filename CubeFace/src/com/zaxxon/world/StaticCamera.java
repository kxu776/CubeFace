package com.zaxxon.world;

import java.awt.GraphicsEnvironment;
import java.util.Timer;
import java.util.TimerTask;

import com.zaxxon.client.MainGame;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Window;
import javafx.util.Duration;

public class StaticCamera {

	private static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getRefreshRate();

	private double positionX = 0;
	private double positionY = 0;
	private double scaleX = 1;
	private double scaleY = 1;
	private Scene renderedScene;
	private Group world;
	private boolean running = false;

	public StaticCamera(Scene renderedScene, Group world) {
		this.renderedScene = renderedScene;
		this.world = world;
	}

	public void start() {
		ScheduledService<Integer> svc = new ScheduledService<Integer>() {
			protected Task<Integer> createTask() {
				return new Task<Integer>() {
					protected Integer call() {
						transformWorld();
						return 0;
					}
				};
			}
		};
		svc.setPeriod(Duration.millis(1000 / FPS));
		svc.start();
	}

	public void kill() {
		this.running = false;
	}

	public void transformWorld() {
		try {
			Bounds b = MainGame.getWorld().getLayoutBounds();
			Window w = MainGame.getRenderedScene().getWindow();
			world.setTranslateX((int) (positionX * scaleX - b.getWidth() / 2 + w.getWidth() / 2));
			world.setTranslateY((int) (positionY * scaleY - b.getHeight() / 2 + w.getHeight() / 2));
			world.setScaleX(scaleX);
			world.setScaleY(scaleY);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void setPosition(double positionX, double positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

}