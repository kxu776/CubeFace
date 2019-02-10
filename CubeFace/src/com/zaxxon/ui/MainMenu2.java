package com.zaxxon.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.FileInputStream;

import static javafx.application.Application.launch;

public class MainMenu2 extends Application {

    Button start;
    Button audio;
    Button help;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //set title of window to be cubeface
        primaryStage.setTitle("CubeFace");

        //make a gridpane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8); //set the gap between cells vertically and horizontally
        grid.setHgap(20);
        grid.setAlignment(Pos.CENTER); //align the grid to the center of the window

        //add column contraints to ensure buttons are in the middle of the cell
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHalignment(HPos.CENTER); //set alignment to center
        grid.getColumnConstraints().add(colConstraints);

        //create the start button
        start = new Button();
        start.setText("Start");
        start.setOnAction(e -> System.out.println("Starting game..."));
        GridPane.setConstraints(start, 0, 3);

        //create the audio button
        audio = new Button();
        audio.setText("Audio");
        audio.setOnAction(e -> System.out.println("Opening audio popup..."));
        audio.setAlignment(Pos.CENTER);
        GridPane.setConstraints(audio, 0, 4);

        //create the help button
        help = new Button();
        help.setText("Help");
        help.setOnAction(e-> System.out.println("Opening help screen..."));
        GridPane.setConstraints(help, 0, 5);

        //load an image
        Image logo = new Image(new FileInputStream("C:\\Users\\Megan\\git\\zaxxon\\CubeFace\\src\\com\\zaxxon\\ui\\cubefacelogo.png"));

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


        //add all buttons to gridpane
        grid.getChildren().addAll(start, audio, help);
        grid.getChildren().add(logoView);

        //make a scene (haha)
        Scene mainmenu = new Scene(grid, 600, 575);
        mainmenu.getStylesheets().add(getClass().getResource("ms.css").toString());




        //set the scene to be the one displayed on the primary stage
        primaryStage.setScene(mainmenu);

        //load the stage (window)
        primaryStage.show();


    }
}
