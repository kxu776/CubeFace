package com.zaxxon.ui;

import com.zaxxon.client.MainGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.awt.*;


public class ArityPopup {


    public static void display(Stage primaryStage, Scene renderedScene)
    {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Player mode");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage



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
                primaryStage.setScene(MainGame.getRenderedScene());
                MainGame.start(primaryStage);
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

        //VBox for the middle
        VBox middle = new VBox();
        middle.getChildren().add(label);
        middle.setAlignment(Pos.CENTER);
        middle.setPadding(new Insets(0, 20, 0, 20));

        //GridPane for the bottom
        GridPane bottom = new GridPane();
        bottom.getChildren().addAll(single, multiplayer);
        bottom.setAlignment(Pos.CENTER);
        bottom.setHgap(20);
        bottom.setPadding(new Insets(0, 20, 20, 20));

        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, true, "Game Mode");


        //borderPane for it all
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbox);
        borderPane.setCenter(middle);
        borderPane.setBottom(bottom);

        //make a rectangle and set clip
        Rectangle rect = new Rectangle(400,150);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        borderPane.setClip(rect);

        //Scene
        Scene scene1= new Scene(borderPane, 400, 150);
        scene1.setFill(Color.TRANSPARENT);
        scene1.getStylesheets().add(ArityPopup.class.getResource("popup.css").toString());
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


}

