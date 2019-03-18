package com.zaxxon.client;

import com.zaxxon.ui.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
		primaryStage.setScene(mainmenu);
		primaryStage.setResizable(false);
		primaryStage.show();
		MainGame.reset(primaryStage);
	}

}
