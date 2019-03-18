package com.zaxxon.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Toolbox {

    public AnchorPane toolbar(Stage popupwindow, int type, String title) {


        //types of toolbar:
        //1 = popup, 2= mainmenu, 3= game


        //make a toolbar (for the top)
        Label tbTitle = new Label(title);
        tbTitle.setAlignment(Pos.BASELINE_LEFT);
        tbTitle.setId("tbTitle");


        Button minimise = new Button();
        //if window is not a popup window
        if (type != 2) {
            minimise.setOnAction(e -> popupwindow.setIconified(true));
        }
        //load the icon
        Image minimiseIcon = new Image(ServerConfirmationPopup.class.getResource("img/minimise.png").toString());
        ImageView minView = new ImageView(minimiseIcon); //make an imageview for the minimise icon
        minimise.setGraphic(minView); //add the image to the button
        minimise.setId("toolbarbutton");

        Button maximise = new Button();
        //if window is not a popup window
        if (type != 2) {
            maximise.setOnAction(e -> popupwindow.setMaximized(true));
        }
        //load the icon
        Image maximiseIcon = new Image(ServerConfirmationPopup.class.getResource("img/maximise.png").toString());
        ImageView maxView = new ImageView(maximiseIcon); //make an imageview for the minimise icon
        maximise.setGraphic(maxView); //add the image to the button
        maximise.setId("toolbarbutton");

        Button close = new Button();
        if (type ==3) {
            close.setOnAction(e -> QuitPopup.display(popupwindow));
            if(type == 2) {
            	
            }
        } else {
            close.setOnAction(e -> popupwindow.close());
        }

        //load the icon
        Image closeIcon = new Image(ServerConfirmationPopup.class.getResource("img/close.png").toString());
        ImageView closeView = new ImageView(closeIcon); //make an imageview for the minimise icon
        close.setGraphic(closeView); //add the image to the button
        close.setId("toolbarbutton");


        //make toolbar layout
        //hbox for icons
        HBox hbox = new HBox();
        hbox.getChildren().addAll(minimise, maximise, close);
        hbox.setAlignment(Pos.BASELINE_RIGHT);



        //anchor pane for all of it
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLeftAnchor(tbTitle, 0.0);
        anchorPane.setRightAnchor(hbox, 0.0);
        anchorPane.getChildren().addAll(tbTitle, hbox);
        anchorPane.setId("toolbar");


        if(type == 1) {
            anchorPane.getStylesheets().add(ArityPopup.class.getResource("popup-toolbox.css").toString());
        } else if(type == 2) {
            anchorPane.getStylesheets().add(ArityPopup.class.getResource("mainmenu-toolbox.css").toString());
        } else if(type ==3) {
            anchorPane.getStylesheets().add(ArityPopup.class.getResource("game-toolbox.css").toString());
        }


        return anchorPane;
    }


}
