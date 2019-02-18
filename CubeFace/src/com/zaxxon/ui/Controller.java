package com.zaxxon.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;


public class Controller {
    public Label startGame;
    public Label openAudioMenu;
    public Label openHelpScreen;
    public Image logo;

    public void startGame(ActionEvent actionEvent) {
        startGame.setText("Starting game...");
    }

    public void openAudioMenu(ActionEvent actionEvent) {
        openAudioMenu.setText("Opening Audio Menu...");
    }

    public void openHelpScreen(ActionEvent actionEvent) {
        openHelpScreen.setText("Opening Help Screen...");
    }

    public void initialiseLogo() {
        File file = new File("src/sample/cubefacelogo.png");
        Image logo = new Image(file.toURI().toString());

    }
}


   /* @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/Box13.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);*/


