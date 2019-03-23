package com.zaxxon.ui.popups;

import com.zaxxon.networking.Server;

import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.tools.Toolbox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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


public class NewServerPopup {
	public static int proceed = 0;

    public static void display(Stage primaryStage, Scene renderedScene)
    {

        double[] xOffset = {0};
        double[] yOffset = {0};
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Make a new server");
        popupwindow.setResizable(false);
        popupwindow.initStyle(StageStyle.TRANSPARENT);


        //****************************CONTENTS

        Label label= new Label("Please enter the following details:");

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
            	int port = 0;
        		String portStr = null;
        		Server server;
    
        		portStr = portField.getText().trim();

        		try {
        			port = Integer.parseInt(portStr);
        		}catch(NumberFormatException e) {
        			return;
        		}
     
            	if(port < 1000 || port > 65535) {
            		System.out.println("Try a different port number ");
            		return;
            	}
            	else {
                //get info from text field and pass to networking
            		server = new Server(port);
            		server.start();
            		
            		try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
				}
            		
            		if(proceed == 1) {
            			return;
            		}
            	
            		
            		// Expect to fail if same port num
            		
                popupwindow.close();
                //open server confirmation popup

                ServerConfirmationPopup.display(primaryStage, renderedScene);
            	}
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

        //border pane for all of the content
        BorderPane content = new BorderPane();
        content.setTop(top);
        content.setCenter(gridPane);
        content.setBottom(bottom);

        AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Make A New Server");

        //another borderpane for everything else
        BorderPane everything = new BorderPane();
        everything.setTop(toolbox);
        everything.setCenter(content);

        //make a rectangle
        Rectangle rect = new Rectangle(400,150);
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);
        everything.setClip(rect);




        Scene scene1= new Scene(everything, 400, 150);
        scene1.setFill(Color.TRANSPARENT);

        popupwindow.setScene(scene1);
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


        popupwindow.show();

    }


}

