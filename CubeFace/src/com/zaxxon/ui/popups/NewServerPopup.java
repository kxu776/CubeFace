package com.zaxxon.ui.popups;

import com.zaxxon.networking.PortNumber;
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
 * New server popup
 * Offers the user the option to make a new server by pressing the button
 * Explains that if they do the ip of the server made will be displayed to the user
 * that they can then use to connect to in the joingame popup
 * @author Megan
 */
public class NewServerPopup {
	public static int proceed = 0;

	/**
	 * when run it builds and runs the new server popup
	 * @param primaryStage
	 * @param renderedScene
	 */
	public static void display(Stage primaryStage, Scene renderedScene) {

		double[] xOffset = { 0 };
		double[] yOffset = { 0 };
		Stage popupwindow = new Stage();

		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Make a new server");
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

		// ****************************CONTENTS


		// ***********ENTER DETAILS

		// server port

		Label port = new Label("Please click below to create a new server\nand you will be informed of the IP address:");
		GridPane.setConstraints(port, 0, 0);


		// make server button

		Button makeServer = new Button("Make a server");
		makeServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Server server;

			// 	portStr = portField.getText().trim();

			
					// get info from text field and pass to networking
					server = new Server(PortNumber.number);
					server.start();	
					// Expect to fail if same port num
					
					try {
						Thread.sleep(1500);
						ServerConfirmationPopup.setIP(server.getServerIP());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					popupwindow.close();
					// open server confirmation popup
					ServerConfirmationPopup.display(primaryStage, renderedScene);					
			}
		});

		// LAYOUT

		// vbox for title
		VBox top = new VBox();
		//top.getChildren().add(label);
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(20, 20, 0, 20));

		// gridpane for the center
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(port, makeServer);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(20);

		// vbox for the bottom
		VBox bottom = new VBox();
		bottom.getChildren().add(makeServer);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(0, 20, 20, 20));

		// border pane for all of the content
		BorderPane content = new BorderPane();
		content.setTop(top);
		content.setCenter(gridPane);
		content.setBottom(bottom);

		AnchorPane toolbox = new Toolbox().toolbar(popupwindow, 1, "Make A New Server");

		// another borderpane for everything else
		BorderPane everything = new BorderPane();
		everything.setTop(toolbox);
		everything.setCenter(content);

		// make a rectangle
		Rectangle rect = new Rectangle(400, 150);
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		everything.setClip(rect);

		Scene scene1 = new Scene(everything, 400, 150);
		scene1.setFill(Color.TRANSPARENT);

		popupwindow.setScene(scene1);
		scene1.getStylesheets().add(MainMenu.class.getResource("css/popup.css").toString());

		// make it movable
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
