package com.zaxxon.world;

import com.zaxxon.client.MainGame;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.stage.Window;

public class StaticCamera extends Camera {
	
	@Override
	public void update(){
		Bounds b = MainGame.getWorld().getLayoutBounds();
		Window w = MainGame.getRenderedScene().getWindow();
		Group world = MainGame.getWorld();
		world.setTranslateX((int) (positionX * scaleX - b.getWidth() / 2 + w.getWidth() / 2));
		world.setTranslateY((int) (positionY * scaleY - b.getHeight() / 2 + w.getHeight() / 2));
		world.setScaleX(scaleX);
		world.setScaleY(scaleY);
	}

}