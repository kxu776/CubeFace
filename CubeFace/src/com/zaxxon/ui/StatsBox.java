package com.zaxxon.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StatsBox {

    public static BorderPane statsBox() {

        //******CENTER

        //health label
        Label health = new Label("HEALTH:");
        health.setId("health");

        //health bar
        //temporary have a label here until i know how to implememnt a "bar" hahah
        Label bar = new Label("\"health bar\"");


        //opponent health label
        Label opHealth = new Label("OPPONENT HEALTH:");
        opHealth.setId("health");

        //opponent health bar
        //temporary have a label here until i know how to implememnt a "bar" hahah
        Label opBar = new Label("\"oponent health bar\"");

        //vbox for health
        VBox center = new VBox(5);
        center.getChildren().addAll(health, bar, opHealth, opBar);


        //*******RIGHT

        //score label
        Label scoreLbl = new Label("SCORE:");
        scoreLbl.setId("scoreLbl");
        scoreLbl.setMaxWidth(80);
        scoreLbl.setMinWidth(80);
        scoreLbl.setTextAlignment(TextAlignment.CENTER);

        //score
        Label score = new Label("*");
        score.setId("score");
        score.setMaxWidth(80);
        score.setMinWidth(80);
        score.setTextAlignment(TextAlignment.CENTER);

        //vbox for score
        VBox right = new VBox();
        right.getChildren().addAll(scoreLbl, score);


        //*******BOTTOM

        //current weapon label
        Label weaponLbl = new Label("CURRENT WEAPON:");
        weaponLbl.setId("weaponLbl");
        weaponLbl.setPadding(new Insets(17, 0, 5, 0));

        //weapon
        Label weapon = new Label("*WEAPON");
        weapon.setId("weapon");
        weapon.setPadding(new Insets(17, 0, 5, 0));

        //Hbox for weapon
        HBox bottom = new HBox();
        bottom.getChildren().addAll(weaponLbl, weapon);



        //BORDER PANE for it all

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(center);
        borderPane.setRight(right);
        borderPane.setBottom(bottom);
        borderPane.setId("pane");


        //borderPane.setMaxHeight(300);
        borderPane.setMaxSize(270, 125);

        borderPane.getStylesheets().add(StatsBox.class.getResource("demo.css").toString());

        //css = Toolkit.getDefaultToolkit().getClass().getResource("img/demo.css");
        //myImage = Toolkit.getDefaultToolkit().getImage(ThisClassName.class.getResource("mygif.gif"));
        //borderPane.getStylesheets().add(css.toString());

        return borderPane;


    }

}
