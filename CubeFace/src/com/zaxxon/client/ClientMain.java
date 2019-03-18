package com.zaxxon.client;

import com.zaxxon.ui.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * the Client for a game to run on/with
 * 
 * @author Philip Eagles
 *
 */
public class ClientMain extends Application {

	/**
	 * runs the client side program
	 * 
	 * @param args command line input options (unused)
	 */
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// load game font
		// load font
		Font.loadFont(MainMenu.class.getResourceAsStream("img/VCR_OSD_MONO.ttf"), 9);
		primaryStage.setTitle("CubeFace");
		MainMenu mainMenu2 = new MainMenu();
		Scene mainmenu = mainMenu2.makeMainMenu(primaryStage);
		// primaryStage.setMaximized(true);
		primaryStage.setScene(mainmenu);
		primaryStage.setResizable(false);
		// primaryStage.setMinWidth(MainMenu.WIDTH);
		// primaryStage.setMinHeight(MainMenu.HEIGHT + 37);
		primaryStage.show();
		MainGame.reset(primaryStage);
		// MainMenu.music.loop();
	}

}
