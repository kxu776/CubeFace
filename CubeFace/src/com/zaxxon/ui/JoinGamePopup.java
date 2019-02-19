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


public class JoinGamePopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Join A Game");

        //****************************CONTENTS

        Label label= new Label("Please enter the following details to join a game:");
        GridPane.setConstraints(label, 0, 0);

        //***********ENTER DETAILS
        Label name = new Label("Player Name:");
        GridPane.setConstraints(name,0,1);
        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 1);

        Label port = new Label("Server Port:");
        GridPane.setConstraints(port, 0, 2);
        TextField portField = new TextField();
        GridPane.setConstraints(portField,1, 2);

        //LAYOUT
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(label, name, nameField, port, portField);
        gridPane.setAlignment(Pos.CENTER);


        Scene scene1= new Scene(gridPane, 350, 150);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }


}

