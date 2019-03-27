package com.zaxxon.ui.popups;

import com.zaxxon.client.MainGame;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.Toolbox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class GameOverPopup {


    public static void display(Stage primaryStage, String score)
    {
        double[] xOffset = {0};
        double[] yOffset = {0};
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Player mode");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage



        //****************************CONTENTS
        //game over label
        Label gameOver = new Label("GAME OVER!");

        Label scoreLbl = new Label("SCORE:");

        Label scoreNum = new Label(score);

        Button finish = new Button("FINISH");
        //finish.setOnAction(e-> primaryStage.setScene());





        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Game Mode");


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
        scene1.getStylesheets().add(MainMenu.class.getResource("css/popup.css").toString());

        //make it movable
        scene1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });

        scene1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popupwindow.setX(event.getScreenX() - xOffset[0]);
                popupwindow.setY(event.getScreenY() - yOffset[0]);
            }
        });


        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


}

