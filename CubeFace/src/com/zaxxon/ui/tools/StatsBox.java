package com.zaxxon.ui.tools;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

public class StatsBox {

    public static Label weapon;
    public static HealthBar healthBar;
    public static HealthBar opHealthBar;
    public static Label score;
    public static Label ammo;

    public static BorderPane statsBox(int type) {

        //types:
        //1=singleplayer
        //2=multiplayer


        //******CENTER

        //health label
        Label health = new Label("HEALTH:");
        health.setId("health");

        //health bar
        healthBar = new HealthBar();
        AnchorPane healthBarPane = healthBar.makeHealthbar();
        healthBarPane.setMinHeight(10);


        //opponent health label
        Label opHealth = new Label("OPPONENT HEALTH:");
        opHealth.setId("health");

        //opponent health bar
        opHealthBar = new HealthBar();
        AnchorPane opHealthBarPane = opHealthBar.makeHealthbar();
        opHealthBarPane.setMinHeight(10);



        //vbox for health
        VBox center = new VBox(5);
        if (type == 1) {
            Label label = new Label(" ");
            label.setPadding(new Insets(25, 0, 0, 0));
            center.getChildren().addAll(label, health, healthBarPane);
        } else {
            center.getChildren().addAll(health, healthBarPane, opHealth, opHealthBarPane);
        }







        //*******RIGHT

        //score label
        Label scoreLbl = new Label("SCORE:");
        scoreLbl.setId("scoreLbl");
        scoreLbl.setMaxWidth(80);
        scoreLbl.setMinWidth(80);
        scoreLbl.setTextAlignment(TextAlignment.CENTER);

        //score
        score = new Label("*");
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

        //weapon
        weapon = new Label("PISTOL");
        weapon.setId("weapon");

        //layout for weapon
        VBox weaponLayout = new VBox(weaponLbl, weapon);


        //ammo label
        Label ammoLbl = new Label("AMMO:");
        ammoLbl.setId("weaponLbl");

        //ammo
        ammo = new Label("UNLIMITED");
        ammo.setId("weapon");


        //layout for ammo
        VBox ammoLayout = new VBox(ammoLbl, ammo);


        //Hbox for weapon and ammo
        HBox bottom = new HBox();
        bottom.getChildren().addAll(weaponLayout, ammoLayout);
        bottom.setSpacing(30);



        //BORDER PANE for it all

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(center);
        borderPane.setRight(right);
        borderPane.setBottom(bottom);
        borderPane.setId("pane");


        //borderPane.setMaxHeight(300);
        borderPane.setMaxSize(270, 125);

        borderPane.getStylesheets().add(StatsBox.class.getResource("../css/statsbox.css").toString());

        return borderPane;


    }

    public static void updateWeapon(String newWeapon) {
        weapon.setText(newWeapon.toUpperCase() );
    }

    public static void updateAmmo(String newAmmo) {

        ammo.setText(newAmmo.toUpperCase() );

    }

    public static void updateHealthBar(Integer newHealth) {
    	healthBar.updateHealthBar(newHealth);
    }

    public static void updateOpHealthBar(Integer newHealth) {
    	opHealthBar.updateHealthBar(newHealth);
    }

    public static void updateScore(Integer newScore) {
        score.setText(newScore.toString());
    }

}
