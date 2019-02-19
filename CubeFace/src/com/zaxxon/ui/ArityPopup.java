package com.zaxxon.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class ArityPopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Player mode");

        //****************************CONTENTS

        Label label = new Label("Choose the mode you would like to play in:");
        GridPane.setConstraints(label, 1, 0);

        //***********SINGLE PLAYER BUTTON
        Button single = new Button("single player");
        GridPane.setConstraints(single,0, 1);
        single.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                primaryStage.setScene(renderedScene);
            }
        });


        //************ MULTIPLAYER BUTTON
        Button multiplayer= new Button("multiplayer");
        GridPane.setConstraints(multiplayer, 2, 1);
        multiplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                JoinGamePopup.display(primaryStage, renderedScene);
            }
        });


        //LAYOUT
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(label, multiplayer, single);
        gridPane.setAlignment(Pos.CENTER);


        Scene scene1= new Scene(gridPane, 350, 150);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }


}

