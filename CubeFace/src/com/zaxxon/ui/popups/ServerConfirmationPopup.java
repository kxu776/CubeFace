package com.zaxxon.ui.popups;

import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.Toolbox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Server confirmation popup
 * Shown after the server has been made from the make server popup
 * tells the user the ip of the serve that has been made
 * and offers to take them back to the join game popup or to exit
 * @author Megan
 */
public class ServerConfirmationPopup {
	private static String ip;

    /**
     * when run it builds and runs the server confirmation popup
     * @param primaryStage
     * @param renderedScene
     */
    public static void display(Stage primaryStage, Scene renderedScene)
    {
        double[] xOffset = {0};
        double[] yOffset = {0};
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT);


        //****************************CONTENTS

        Label label= new Label("Your server has been successfully made!");
        
        Label ipLabel= new Label("The IP is for the server is: " + ip);



        //join game
        Button joinGame = new Button("Join Game");
        GridPane.setConstraints(joinGame, 3,3);
        joinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupwindow.close();
                JoinGamePopup.display(primaryStage, renderedScene);
            }
        });


        //exit button
        Button exit = new Button("Exit");
        GridPane.setConstraints(exit, 0, 3);
        exit.setOnAction(e->popupwindow.close());

        //make a hbox to hold the button
        HBox buttons = new HBox(joinGame, exit);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        //make a Vbox to hold the conetnt
        VBox content = new VBox(label, ipLabel, buttons );
        content.setSpacing(12);
        content.setAlignment(Pos.CENTER);




        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Congratulations!");

        //borderPane for it all
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbox);
        borderPane.setCenter(content);

        //make a rectangle and set clip
        Rectangle rect = new Rectangle(450,150);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        borderPane.setClip(rect);




        //make a scene
        Scene scene1= new Scene(borderPane, 450, 150);
        popupwindow.setScene(scene1);
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


        popupwindow.showAndWait();

    }
    public static void setIP(String IP) {
    		ip = IP;
    }


}

