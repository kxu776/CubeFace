package com.zaxxon.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HealthBar {

    public AnchorPane healthbar() {

        //make a new healthbar

        //make an anchorpane to hold the healthbar
        AnchorPane healthbar = new AnchorPane();

        //make a new rectangle for background
        Rectangle red = new Rectangle(100, 10);
        red.setFill(Color.RED);

        //make a new rectangle for foreground
        Rectangle green = new Rectangle(70, 10);
        green.setFill(Color.LIME);

        //add rectangles to HBox
        healthbar.getChildren().addAll(red, green);

        return healthbar;


    }


}
