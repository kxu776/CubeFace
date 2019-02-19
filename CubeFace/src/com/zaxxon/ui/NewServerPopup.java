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


public class NewServerPopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Make a new server");

        //****************************CONTENTS

        Label label= new Label("Please enter the following details\nto make a new server:");
        GridPane.setConstraints(label, 0, 0);

        //***********ENTER DETAILS



        //server port

        Label port = new Label("Server Port:");
        GridPane.setConstraints(port, 0, 1);
        TextField portField = new TextField();
        GridPane.setConstraints(portField,1, 1);

        //make server button

        Button makeServer = new Button("Make server");
        GridPane.setConstraints(makeServer, 0, 2);
        makeServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //get info from text field and pass to networking

                popupwindow.close();
                //open server confirmation popup

                ServerConfirmationPopup.display(primaryStage, renderedScene);
            }
        });





        //LAYOUT
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(label, port, portField, makeServer);
        gridPane.setAlignment(Pos.CENTER);


        Scene scene1= new Scene(gridPane, 350, 150);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }


}

