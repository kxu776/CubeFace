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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class JoinGamePopup {

    public static void display(Stage primaryStage, Scene renderedScene)
    {

        //************************INITIALISE THE STAGE*************************
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Join A Game");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage




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
        portField.setPromptText("1111");
        GridPane.setConstraints(portField,1, 1);



        //*****START GAME BUTTON*****

        Button startGame = new Button("Go");
        GridPane.setConstraints(startGame, 0, 2);
        startGame.setOnAction(e -> {
        		MainGame.networkingClient = new Client("172.20.10.2",Integer.parseInt((portField.getText())),nameField.getText());
    			MainGame.networkingClient.start();
    			MainGame.multiplayer = true;
            popupwindow.close();
            primaryStage.setScene(MainGame.getRenderedScene());
            MainGame.start(primaryStage);
        });
        startGame.setId("startgame");


        //*****NEW SERVER BUTTON*****

        Button newServer = new Button("Or click here to make a new server");
        GridPane.setConstraints(newServer,1,2);
        newServer.setOnAction(e -> {
            popupwindow.close();
            NewServerPopup.display(primaryStage, renderedScene);
        });
        newServer.setId("newsever");



        //******************LAYOUT******************


        //gridpane for the details
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(nameField, portField, name, port);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //vbox for content
        VBox top = new VBox(15);
        top.getChildren().addAll(label, gridPane);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 20, 20, 20));

        //hbox for the bottom
        HBox bottom = new HBox(20);
        bottom.getChildren().add(startGame);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(0, 20, 20, 20));

        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Join A Game");

        //borderpane for everything - new server button
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbox);
        borderPane.setCenter(top);
        borderPane.setBottom(bottom);

        //another anchorpane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setTopAnchor(borderPane, 0.0);
        anchorPane.setRightAnchor(borderPane,0.0);
        anchorPane.setBottomAnchor(newServer, 1.0);
        anchorPane.setRightAnchor(newServer, 1.0);
        anchorPane.getChildren().addAll(borderPane, newServer);

        Rectangle rect = new Rectangle(394,200);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        borderPane.setClip(rect);


        //**********************SCENE******************

        Scene scene1= new Scene(anchorPane, 394, 200);
        scene1.getStylesheets().add(ArityPopup.class.getResource("popup.css").toString());
        scene1.setFill(Color.TRANSPARENT);
        popupwindow.setScene(scene1);

        popupwindow.show();

    }


}

