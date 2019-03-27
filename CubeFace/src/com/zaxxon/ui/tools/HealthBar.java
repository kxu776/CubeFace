package com.zaxxon.ui.tools;

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

    //make an anchorpane to hold the healthbar
    public AnchorPane healthbar;
    public Rectangle red; //rectangles to make healthbar
    public Rectangle green; //health is initially

    public AnchorPane makeHealthbar() {

        //initialise
        healthbar = new AnchorPane();
        red = new Rectangle(100, 10); //rectangles to make healthbar
        green = new Rectangle(100, 10); //health is initially

        //set background rectangle to be red
        red.setFill(Color.RED);

        //make set health rectangle to be green
        green.setFill(Color.LIME);

        //add rectangles to HBox
        healthbar.getChildren().addAll(red, green);

        return healthbar;

    }

    public void updateHealthBar(Integer newHealth) {

        if (newHealth > 100) {
            green.setWidth(100);
        } else if (newHealth < 0) {
            green.setWidth(0);
        } else {
            green.setWidth(newHealth);
        }

    }


}
