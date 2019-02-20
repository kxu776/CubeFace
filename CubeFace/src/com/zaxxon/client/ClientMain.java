package com.zaxxon.client;

import com.zaxxon.ui.MainMenu2;

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
		MainGame game = new MainGame();
		MainMenu2 mainMenu2 = new MainMenu2();
		Scene mainmenu = mainMenu2.makeMainMenu(game, primaryStage);
		primaryStage.setMaximized(true);
		primaryStage.setScene(mainmenu);
		primaryStage.show();
	}

}
