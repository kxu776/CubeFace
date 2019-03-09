package com.zaxxon.client;

import java.awt.geom.Point2D.Double;

import com.zaxxon.ui.MainMenu;

import com.zaxxon.ui.StatsBox;
import com.zaxxon.world.Levels;
import com.zaxxon.world.Tile;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import sun.applet.Main;

public class ClientMain extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//load game font
		//load font
		Font.loadFont(MainMenu.class.getResourceAsStream("img/VCR_OSD_MONO.ttf"), 9);
		primaryStage.setTitle("CubeFace");
		MainMenu mainMenu2 = new MainMenu();
		Scene mainmenu = mainMenu2.makeMainMenu(primaryStage);
		//primaryStage.setMaximized(true);
		primaryStage.setScene(mainmenu);
		primaryStage.setResizable(false);
		//primaryStage.setMinWidth(MainMenu.WIDTH);
		//primaryStage.setMinHeight(MainMenu.HEIGHT + 37);
		primaryStage.show();
		MainGame.reset(primaryStage);
	//	MainMenu.music.loop();
		System.out.println(Levels.L2_WAYPOINTS.length);
		for(java.awt.geom.Point2D.Double pd : Levels.L2_WAYPOINTS) {
			Tile t = new Tile(pd.getX(), pd.getY(), Color.BLUE);
			MainGame.addSpriteToBackground(t);
		}
	}

}
