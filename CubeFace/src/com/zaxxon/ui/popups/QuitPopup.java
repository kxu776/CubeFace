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

/**
 * Quit popup
 * Shown if the user presses the close button whilst the game is running
 * asks the user if they are sure they would like to quit
 * @author Megan
 */
public class QuitPopup {


    /**
     * when run it builds and runs the quit popup
     * @param primaryStage
     */
    public static void display(Stage primaryStage)
    {
        double[] xOffset = {0};
        double[] yOffset = {0};
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Player mode");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage



        //****************************CONTENTS

        Label label = new Label("Are you sure you would like to quit?");
        GridPane.setConstraints(label, 0, 0);

        //***********YES - quit
        Button single = new Button("Yes");
        GridPane.setConstraints(single,0, 0);
        single.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(MainGame.multiplayer) {
            		MainGame.getNetworkingClient().disconnect();
            	}
                popupwindow.close();
                primaryStage.setResizable(false);
                primaryStage.setScene(MainMenu.mainmenu);
            
        }});


        //************ NO - continue game
        Button multiplayer= new Button("No");
        GridPane.setConstraints(multiplayer, 1, 0);
        multiplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	MainGame.setGameFocus();
                popupwindow.close();
                //JoinGamePopup.display(primaryStage, renderedScene);
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

