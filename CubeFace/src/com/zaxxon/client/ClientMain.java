package com.zaxxon.client;

import com.zaxxon.ui.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static com.zaxxon.ui.MainMenu.music;

/**
 * the Client for a game to run on/with
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
		// load font
		Font.loadFont(MainMenu.class.getResourceAsStream("../ui/VCR_OSD_MONO.ttf"), 9);
		primaryStage.setTitle("CubeFace");
		// set up the main menu
		MainMenu mainMenu2 = new MainMenu();
		Scene mainmenu = mainMenu2.makeMainMenu(primaryStage);
		// prevent rescaling
		primaryStage.setResizable(false);
		primaryStage.setScene(mainmenu);
		primaryStage.show();
		// set up the main game
		MainGame.setMusic(music);
		MainGame.reset(primaryStage);
	}

}
