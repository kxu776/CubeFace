package com.zaxxon.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ServerConfirmationPopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Success!");
        popupwindow.setResizable(false);


        //****************************CONTENTS

        Label label= new Label("Your server has been successfully made!");
        GridPane.setConstraints(label, 0, 0);

        //join game
        Button joinGame = new Button("Join Game");
        GridPane.setConstraints(joinGame, 1, 1);
        joinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                JoinGamePopup.display(primaryStage, renderedScene);
            }
        });


        //exit button
        Button exit = new Button("Exit");
        GridPane.setConstraints(exit, 0, 1);
        exit.setOnAction(e->popupwindow.close());



        //LAYOUT
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(label, exit, joinGame);
        gridPane.setAlignment(Pos.CENTER);


        Scene scene1= new Scene(gridPane, 400, 150);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }


}

