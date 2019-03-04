package com.zaxxon.client;

import com.zaxxon.ui.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CubeFace");
		MainMenu mainMenu2 = new MainMenu();
		Scene mainmenu = mainMenu2.makeMainMenu(primaryStage);
		primaryStage.setMaximized(true);
		primaryStage.setScene(mainmenu);
		primaryStage.setMinWidth(MainMenu.WIDTH);
		primaryStage.setMinHeight(MainMenu.HEIGHT + 37);
		primaryStage.show();
		MainGame.reset(primaryStage);
	//	MainMenu.music.loop();
	}

}
