package com.zaxxon.ui;

import com.zaxxon.client.MainGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class ArityPopup {


    public static void display(Stage primaryStage, Scene renderedScene, MainGame mainGame)
    {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Player mode");
        popupwindow.setResizable(false);


        //****************************CONTENTS

        Label label = new Label("Choose the mode you would like to play in:");
        GridPane.setConstraints(label, 0, 0);

        //***********SINGLE PLAYER BUTTON
        Button single = new Button("single player");
        GridPane.setConstraints(single,0, 0);
        single.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                primaryStage.setScene(renderedScene);
                if(mainGame != null) {
                	mainGame.start(primaryStage);
                }
            }
        });


        //************ MULTIPLAYER BUTTON
        Button multiplayer= new Button("multiplayer");
        GridPane.setConstraints(multiplayer, 1, 0);
        multiplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                JoinGamePopup.display(primaryStage, renderedScene);
            }
        });


        //********************LAYOUT**************************

        //VBox for the top
        VBox top = new VBox();
        top.getChildren().add(label);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(40, 20, 0, 20));

        //GridPane for the middle
        GridPane center = new GridPane();
        center.getChildren().addAll(single, multiplayer);
        center.setAlignment(Pos.CENTER);
        center.setHgap(20);

        //borderPane for both
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setCenter(center);

        //Scene
        Scene scene1= new Scene(borderPane, 400, 150);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


}

