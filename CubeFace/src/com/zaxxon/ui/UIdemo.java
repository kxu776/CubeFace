package com.zaxxon.ui;

import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;

public class UIdemo extends Application {

    Stage window;
    Button start;
    Button audio;
    Button help;

    public static void main(String args[]) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Label label1= new Label("game will b here");
        GridPane tempgrid = new GridPane();
        tempgrid.getChildren().addAll(label1);
        tempgrid.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(tempgrid, 600, 575);



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

        start = new Button("start");
        start.setOnAction(e -> ArityPopup.display(primaryStage, scene1));
        GridPane.setConstraints(start, 0, 3);
        //load the start button text
        //Image startText = new Image(getClass().getResource("img\\start.png").toString());
        //ImageView startView = new ImageView(startText); //make an imageview for the start button's text
        //start.setGraphic(startView); //add the image to the button


        //**************************LOGO****************************
        //load the logo image
        Image logo = new Image(getClass().getResource("img/cubefacelogo.png").toString());

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


        //*******************************************MAKE A TEST STATS GROUP

        //make a backgroud label
        Label bg = new Label(" ");
        GridPane.setConstraints(bg, 0, 0);
        bg.setPadding(new Insets(60, 100, 60, 100));
        bg.setStyle("-fx-background-color: grey;");

        //make a new group
        GridPane stats = new GridPane();
        stats.getChildren().add(bg);


        //***********************************************************************************
        //***********************************************************************************

        //******CENTER

        //health label
        Label health = new Label("HEALTH:");
        health.setId("health");

        //health bar
        //temporary have a label here until i know how to implememnt a "bar" hahah
        Label bar = new Label("\"health bar\"");


        //opponent health label
        Label opHealth = new Label("OPPONENT HEALTH:");
        opHealth.setId("health");

        //opponent health bar
        //temporary have a label here until i know how to implememnt a "bar" hahah
        Label opBar = new Label("\"oponent health bar\"");

        //vbox for health
        VBox center = new VBox(5);
        center.getChildren().addAll(health, bar, opHealth, opBar);


        //*******RIGHT

        //score label
        Label scoreLbl = new Label("SCORE:");
        scoreLbl.setId("scoreLbl");
        scoreLbl.setMaxWidth(80);
        scoreLbl.setMinWidth(80);

        //score
        Label score = new Label("*");
        score.setId("score");
        score.setMaxWidth(80);
        score.setMinWidth(80);

        //vbox for score
        VBox right = new VBox();
        right.getChildren().addAll(scoreLbl, score);


        //*******BOTTOM

        //current weapon label
        Label weaponLbl = new Label("CURRENT WEAPON:");
        weaponLbl.setId("health");
        weaponLbl.setPadding(new Insets(10, 0, 0, 0));

        //weapon
        Label weapon = new Label("*weapon");
        weapon.setId("weapon");
        weapon.setPadding(new Insets(10, 0, 0, 0));

        //Hbox for weapon
        HBox bottom = new HBox();
        bottom.getChildren().addAll(weaponLbl, weapon);



        //BORDER PANE for it all

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(center);
        borderPane.setRight(right);
        borderPane.setBottom(bottom);
        borderPane.setId("pane");
        

        //borderPane.setMaxHeight(300);
        borderPane.setMaxSize(270, 150);



        //***********************************************************************************
        //***********************************************************************************


        //****************************GRIDPANES AND SCENE************************************

        //add all buttons to gridpane
        grid1.getChildren().add(start);
        grid1.getChildren().add(logoView);



        //make a scene (haha)
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().addAll(grid1, borderPane);

        Scene mainmenu = new Scene(rootPane, 600, 575);
        mainmenu.getStylesheets().add(getClass().getResource("demo.css").toString()); //add the stylesheet

        //set the scene to be the one displayed on the window
        window.setScene(mainmenu);

        //load the window
        window.show();
//        System.out.println(getClass().getResource("cubefacelogo.png").toString());
    }
}
