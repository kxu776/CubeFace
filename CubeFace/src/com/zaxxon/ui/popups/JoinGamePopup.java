package com.zaxxon.ui.popups;

import com.zaxxon.client.MainGame;
import com.zaxxon.networking.Client;
import com.zaxxon.networking.PortNumber;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.Toolbox;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Join game popup - is shown if the user chooses multiplayer after the modepopup
 * allows user to enter the ip of the game they want to join and set a username
 * or has another button if they want to create a server
 * @author Megan
 */
public class JoinGamePopup {

    /**
     * Display - when run it builds and runs the joingame popup
     * @param primaryStage
     * @param renderedScene
     */
    public static void display(Stage primaryStage, Scene renderedScene)
    {
        double[] xOffset = {0};
        double[] yOffset = {0};

        //************************INITIALISE THE STAGE*************************
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Join A Game");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage




        //****************************CONTENTS*********************************

        //*****TITLE LABEL*****

        Label label= new Label("Please enter the following details to join a game:");



        //**************************ENTER DETAILS*******************************

        //*****PLAYER NAME*****

        Label name = new Label("Player Name:");
        GridPane.setConstraints(name,0,0);

        final TextField nameField = new TextField();
        nameField.setPromptText("player1");
        GridPane.setConstraints(nameField, 1, 0);

        //******* SERVER IP***************
        Label serverIP = new Label("Server IP:");
        GridPane.setConstraints(serverIP, 0, 1);

        TextField ipField = new TextField(null);
        ipField.setPromptText("127.0.0.1");
        GridPane.setConstraints(ipField,1,1);

        //*****SERVER PORT*****

       // Label port = new Label("Server Port:");
      //  GridPane.setConstraints(port, 0, 2);

        // TextField portField = new TextField(""+PortNumber.number);
        // GridPane.setConstraints(portField,1, 2);



        //*****START GAME BUTTON*****

        Button startGame = new Button("Go");
        GridPane.setConstraints(startGame, 0, 2);
        startGame.setOnAction(e -> {
        		String ip = ipField.getText();
        		System.out.println(ipField.getText());
        	 	MainGame.setUpClientThread(ip,PortNumber.number,nameField.getText());
    			MainGame.multiplayer = true;
            popupwindow.close();
            primaryStage.setScene(MainGame.getRenderedScene());
            MainGame.reset(primaryStage);
            MainGame.start(primaryStage);
        });
        startGame.setId("startgame");


        //*****NEW SERVER BUTTON*****

        Button newServer = new Button("Or click here to make a new server");
        GridPane.setConstraints(newServer,1,2);
        newServer.setOnAction(e -> {
            popupwindow.close();
            NewServerPopup.display(primaryStage, renderedScene);
        });
        newServer.setId("newsever");





        //******************LAYOUT******************


        //gridpane for the details
        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(nameField, ipField, serverIP, name);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //vbox for content
        VBox top = new VBox(15);
        top.getChildren().addAll(label, gridPane);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 20, 20, 20));

        //hbox for the bottom
        HBox bottom = new HBox(30);
        bottom.getChildren().add(startGame);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(0, 0, 40, 40));

        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Join A Game");

        //borderpane for everything - new server button
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbox);
        borderPane.setCenter(top);
        borderPane.setBottom(bottom);

        //another anchorpane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setTopAnchor(borderPane, 0.0);
        anchorPane.setRightAnchor(borderPane,0.0);
        anchorPane.setBottomAnchor(newServer, 1.0);
        anchorPane.setRightAnchor(newServer, 1.0);
        anchorPane.getChildren().addAll(borderPane, newServer);

        Rectangle rect = new Rectangle(394,230);
        rect.setArcHeight(11.0);
        rect.setArcWidth(11.0);
        anchorPane.setClip(rect);


        //**********************SCENE******************

        Scene scene1= new Scene(anchorPane, 394, 230);
        scene1.setFill(Color.TRANSPARENT);
        scene1.getStylesheets().add(MainMenu.class.getResource("css/popup.css").toString());

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

        popupwindow.show();

    }


}

