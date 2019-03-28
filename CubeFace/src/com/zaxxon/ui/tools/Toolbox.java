package com.zaxxon.ui.tools;

import com.zaxxon.client.MainGame;
import com.zaxxon.ui.MainMenu;
import com.zaxxon.ui.popups.QuitPopup;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Toolbox {
	public static boolean runningServer = false;

    public AnchorPane toolbar(Stage popupwindow, int type, String title) {


        //types of toolbar:
        //1 = popup, 2= mainmenu, 3= game, 4= helpscreen 5=gameover

        //make a toolbar (for the top)
        Label tbTitle = new Label(title);
        tbTitle.setAlignment(Pos.BASELINE_LEFT);
        tbTitle.setId("tbTitle");


        Button minimise = new Button();
        //if window is not a popup window
        if (type != 1) {
            minimise.setOnAction(e -> {
            	popupwindow.setIconified(true);
            	MainGame.setGameFocus();
            });
        }
        //load the icon
        Image minimiseIcon = new Image(MainMenu.class.getResource("img/minimise.png").toString());
        ImageView minView = new ImageView(minimiseIcon); //make an imageview for the minimise icon
        minimise.setGraphic(minView); //add the image to the button
        minimise.setId("toolbarbutton");



        Button close = new Button();
        close.setOnAction(e->{
            if(type ==3) {
                QuitPopup.display(popupwindow);
                MainGame.setGameFocus();
        		MainGame.stop();
        		MainGame.reset(popupwindow);
            } else {
            	if(type == 2) {
                 popupwindow.close();
            		System.exit(0);
            		}
                popupwindow.close();
            }
        });



        //load the icon
        Image closeIcon = new Image(MainMenu.class.getResource("img/close.png").toString());
        ImageView closeView = new ImageView(closeIcon); //make an imageview for the minimise icon
        close.setGraphic(closeView); //add the image to the button
        close.setId("toolbarbutton");

        //make toolbar layout
        //hbox for icons
        HBox hbox = new HBox();
        if (type != 5) {
            hbox.getChildren().addAll(minimise, close);
            hbox.setAlignment(Pos.BASELINE_RIGHT);
        }




        //anchor pane for all of it
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLeftAnchor(tbTitle, 0.0);
        anchorPane.setRightAnchor(hbox, 0.0);
        anchorPane.getChildren().addAll(tbTitle, hbox);
        anchorPane.setId("toolbar");


        if(type == 1) {
            anchorPane.getStylesheets().add(Toolbox.class.getResource("../css/popup-toolbox.css").toString());
        } else if(type == 2) {
            anchorPane.getStylesheets().add(Toolbox.class.getResource("../css/mainmenu-toolbox.css").toString());
        } else if(type == 3) {
            anchorPane.getStylesheets().add(Toolbox.class.getResource("../css/game-toolbox.css").toString());
        } else if(type == 4) {
            anchorPane.getStylesheets().add(Toolbox.class.getResource("../css/help-toolbox.css").toString());
        }


        return anchorPane;
    }

}
