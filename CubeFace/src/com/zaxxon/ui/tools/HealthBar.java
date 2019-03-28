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

/**
 * HealthBar
 * Makes a healthbar to be displayed on the stats box to display the level the users health is at
 * @author Megan
 */
public class HealthBar {

    //make an anchorpane to hold the healthbar
    public AnchorPane healthbar;
    public Rectangle red; //rectangles to make healthbar
    public Rectangle green; //health is initially

    /**
     * Makes a healthbar and returns the healthbar made
     * healthbar consits of a red rectangle and green rectangle on top
     * the green rectangle changes size based on the players health
     * @return
     */
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

    /**
     * Update healthbar
     * if the health of the user changes, this method is called
     * and chneges the size of the green rectangle to display the change in health
     * @param newHealth
     */
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
