package com.zaxxon.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Controller {
    public Label startGame;
    public Label openAudioMenu;
    public Label openHelpScreen;

    //load the cubefacelogo
    Image image = new Image(new FileInputStream("C:\\Users\\Megan\\git\\zaxxon\\CubeFace\\src\\com\\zaxxon\\ui\\cubefacelogo.png"));

    public Controller() throws FileNotFoundException {
    }

    //set the imageview
    ImageView imageView = new ImageView(image);




    public void startGame(ActionEvent actionEvent) {
        startGame.setText("Starting game...");
    }

    public void openAudioMenu(ActionEvent actionEvent) {
        openAudioMenu.setText("Opening Audio Menu...");
    }

    public void openHelpScreen(ActionEvent actionEvent) {
        openHelpScreen.setText("Opening Help Screen...");
    }


}




