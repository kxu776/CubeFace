package com.zaxxon.ui;

import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.FileInputStream;

import static javafx.application.Application.launch;

public class MainMenu extends Application {

    Stage window;
    Button start;
    Button audio;
    Button help;

    public static void main(String args[]) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //set primary stage = window to make code more readable
        window = primaryStage;

        //set title of window to be cubeface
        window.setTitle("CubeFace");

        //******************************GRIDPANE 1***********************************

        //make a gridpane for the bg, buttons and logo
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(20, 20, 20, 20));
        grid1.setVgap(12); //set the gap between cells vertically and horizontally
        grid1.setHgap(20);

        //add column contraints to ensure buttons are in the middle of the cell
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHalignment(HPos.CENTER); //set alignment to center
        grid1.getColumnConstraints().add(colConstraints);


        //******************************BUTTONS**********************************

        //START BUTTON

        start = new Button();
        //start.setOnAction(e -> window.setScene(renderedScene)));
        GridPane.setConstraints(start, 0, 3);
        //load the start button text
        Image startText = new Image(getClass().getResource("start.png").toString());
        ImageView startView = new ImageView(startText); //make an imageview for the start button's text
        start.setGraphic(startView); //add the image to the button


        //AUDIO BUTTON

        audio = new Button();
        audio.setOnAction(e -> System.out.println("Opening audio popup..."));
        GridPane.setConstraints(audio, 0, 4);
        //load the audio button text
        Image audioText = new Image(getClass().getResource("audio.png").toString());
        ImageView audioView = new ImageView(audioText); //make image view for audio button's text
        audio.setGraphic(audioView); //add image to button


        //HELP BUTTON

        help = new Button();
        help.setOnAction(e-> System.out.println("Opening help screen..."));
        GridPane.setConstraints(help, 0, 5);
        //load the help button's text
        Image helpText = new Image(getClass().getResource("help.png").toString());
        ImageView helpView = new ImageView(helpText); //make image view for help button's text
        help.setGraphic(helpView); //add image to button



        //**************************LOGO****************************
        //load the logo image
        Image logo = new Image(getClass().getResource("cubefacelogo.png").toString());

        //set the imageview
        ImageView logoView = new ImageView(logo);

        //set the position of the image
        logoView.setX(50);
        logoView.setY(25);

        //set height/width of image
        //logoView.setFitHeight(455);
        logoView.setFitWidth(450);

        //preseve ratio of image
        logoView.setPreserveRatio(true);
/*
        //************************************GRIDPANE 2*************************************
        //make a gridpane for the bg, buttons and logo
        GridPane grid2 = new GridPane();
        grid1.setPadding(new Insets(20, 20, 20, 20));
        grid1.setVgap(12); //set the gap between cells vertically and horizontally
        grid1.setHgap(20);

        //IMAGES

        //load the zombie image
        //load the logo image
        Image zombie = new Image(getClass().getResource("zombie.png").toString());

        //set the imageview
        ImageView zombieView = new ImageView(logo);

        //set the position of the image
        zombieView.setX(20);
        zombieView.setY(20);

        //set height/width of image
        //logoView.setFitHeight(455);
        //logoView.setFitWidth(450);

        //preseve ratio of image
        zombieView.setPreserveRatio(true);


        //load the cubeface image*/



        //****************************GRIDPANES AND SCENE************************************

        //add all buttons to gridpane
        grid1.getChildren().addAll(start, audio, help);
        grid1.getChildren().add(logoView);

        //make a scene (haha)
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(grid1);

        Scene mainmenu = new Scene(rootPane, 600, 575);
        mainmenu.getStylesheets().add(getClass().getResource("ms.css").toString()); //add the stylesheet

        //set the scene to be the one displayed on the window
        window.setScene(mainmenu);

        //load the window
        window.show();

        System.out.println(getClass().getResource("cubefacelogo.png").toString());
    }

}
