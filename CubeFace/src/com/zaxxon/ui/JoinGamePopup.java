package com.zaxxon.ui;

import com.zaxxon.client.MainGame;
import com.zaxxon.networking.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class JoinGamePopup {

    public static void display(Stage primaryStage, Scene renderedScene)
    {

        //************************INITIALISE THE STAGE*************************
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Join A Game");
        popupwindow.setResizable(false);




        //****************************CONTENTS*********************************

        //*****TITLE LABEL*****

        Label label= new Label("Please enter the following details to join a game:");



        //**************************ENTER DETAILS*******************************

        //*****PLAYER NAME*****

        Label name = new Label("Player Name:");
        GridPane.setConstraints(name,0,0);

        final TextField nameField = new TextField();
        nameField.setPromptText("player1");
        GridPane.setConstraints(nameField, 1, 0);


        //*****SERVER PORT*****

        Label port = new Label("Server Port:");
        GridPane.setConstraints(port, 0, 1);

        TextField portField = new TextField();
        GridPane.setConstraints(portField,1, 1);



        //*****START GAME BUTTON*****

        Button startGame = new Button("Go");
        GridPane.setConstraints(startGame, 0, 2);
        startGame.setOnAction(e -> {
        	MainGame.multiplayer = true;

            popupwindow.close();
            primaryStage.setScene(MainGame.getRenderedScene());
            MainGame.start(primaryStage);
        });


        //*****NEW SERVER BUTTON*****

        Button newServer = new Button("Or click here to make a new server");
        GridPane.setConstraints(newServer,1,2);
        newServer.setOnAction(e -> {
            popupwindow.close();
            NewServerPopup.display(primaryStage,renderedScene);
        });



        //******************LAYOUT******************


        //gridpane for the center
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(nameField, portField, name, port);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //vbox for title
        VBox top = new VBox();
        top.getChildren().add(label);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 20, 0, 20));

        //hbox for the bottom
        HBox bottom = new HBox(20);
        bottom.getChildren().addAll(startGame, newServer);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(0, 20, 20, 20));

        //borderpane for everything
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottom);


        //**********************SCENE******************

        Scene scene1= new Scene(borderPane, 400, 180);
        popupwindow.setScene(scene1);

        popupwindow.show();

    }


}

