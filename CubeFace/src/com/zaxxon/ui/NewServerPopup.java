package com.zaxxon.ui;

import com.zaxxon.networking.Server;

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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NewServerPopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Make a new server");
        popupwindow.setResizable(false);


        //****************************CONTENTS

        Label label= new Label("Please enter the following details to make a new server:");

        //***********ENTER DETAILS



        //server port

        Label port = new Label("Server Port:");
        GridPane.setConstraints(port, 0, 0);
        TextField portField = new TextField();
        GridPane.setConstraints(portField,1, 0);

        //make server button

        Button makeServer = new Button("Make server");
        makeServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //get info from text field and pass to networking
            		new Server(Integer.parseInt(portField.getText())).start();;
                popupwindow.close();
                //open server confirmation popup

                ServerConfirmationPopup.display(primaryStage, renderedScene);
            }
        });





        //LAYOUT

        //vbox for title
        VBox top = new VBox();
        top.getChildren().add(label);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 20, 0, 20));

        //gridpane for the center
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(port, portField, makeServer);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);

        //vbox for the bottom
        VBox bottom = new VBox();
        bottom.getChildren().add(makeServer);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(0, 20, 20, 20));

        //border pane for all of it
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottom);


        Scene scene1= new Scene(borderPane, 400, 150);

        popupwindow.setScene(scene1);
        scene1.getStylesheets().add(ArityPopup.class.getResource("popup.css").toString());
        popupwindow.show();

    }


}

