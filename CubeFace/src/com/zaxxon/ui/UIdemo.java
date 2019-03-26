package com.zaxxon.ui;

import com.zaxxon.ui.popups.ArityPopup;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class UIdemo extends Application {

    Stage window;
    Button start;
    Button audio;
    Button help;

    public static void main(String args[]) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        window = new Stage();

        //load font
        Font.loadFont(getClass().getResourceAsStream("VCR_OSD_MONO.ttf"), 9);



        //make a title for the screen
        Label title = new Label("HELP");
        title.setPadding(new Insets(20, 0, 0, 0));




        //make a movement label
        Label movement = new Label("MOVEMENT:");
        movement.setPadding(new Insets(0, 0, 15, 0));

        //load the wasd image
        Image wasd = new Image(HelpScreen.class.getResource("img/wasd.png").toString());
        ImageView wasdView = new ImageView(wasd);


        //make a shooting label
        Label shoot = new Label("SHOOT:");
        shoot.setPadding(new Insets(0, 0, 15, 0));

        //load the spacebar view
        Image space = new Image(HelpScreen.class.getResource("img/space.png").toString());
        ImageView spaceView = new ImageView(space);
        spaceView.setPreserveRatio(true);
        spaceView.setFitHeight(50);

        //make a blank label to add padding between movement and shoot
        Label padding = new Label("");
        padding.setPadding(new Insets(20, 0, 0, 0));

        //make a vbox for the above
        VBox left = new VBox(movement, wasdView, padding, shoot, spaceView);
        left.setAlignment(Pos.CENTER);




        //make a change weapon label
        Label changeWeapon = new Label("CHANGE WEAPON:");
        changeWeapon.setPadding(new Insets(20, 0, 15, 0));

        //load the shift image
        Image shift = new Image(HelpScreen.class.getResource("img/shift.png").toString());
        ImageView shiftView = new ImageView(shift);
        shiftView.setPreserveRatio(true);
        shiftView.setFitHeight(50);

        //make a + label
        Label plus = new Label(("+"));
        plus.setId("plus");

        //load the 1, 2, 3 buttons images
        Image one = new Image(HelpScreen.class.getResource("img/1.png").toString());
        ImageView oneView = new ImageView(one);
        oneView.setPreserveRatio(true);
        oneView.setFitHeight(50);

        Image two = new Image(HelpScreen.class.getResource("img/2.png").toString());
        ImageView twoView = new ImageView(two);
        twoView.setPreserveRatio(true);
        twoView.setFitHeight(50);

        Image three = new Image(HelpScreen.class.getResource("img/3.png").toString());
        ImageView threeView = new ImageView(three);
        threeView.setPreserveRatio(true);
        threeView.setFitHeight(50);

        //add the 1, 2, 3 buttons to a vbox
        VBox numbers = new VBox(oneView, twoView, threeView);
        numbers.setSpacing(10);

        //add the graphics to a hbox
        HBox changeWeaponContent = new HBox(shiftView, plus, numbers);

        //make a borderpane to hold the weapon change info
        BorderPane changeWeaponLayout = new BorderPane();
        changeWeaponLayout.setCenter(changeWeaponContent);
        changeWeaponLayout.setTop(changeWeapon);
        changeWeaponLayout.setAlignment(changeWeapon, Pos.CENTER);

        //make a gridpane for all the content
        GridPane content = new GridPane();
        content.setConstraints(left, 0, 0);
        content.setConstraints(changeWeaponLayout, 1, 0);
        content.getChildren().addAll(left,changeWeaponLayout);
        content.setAlignment(Pos.CENTER);
        content.setHgap(35);

        //borderpane for everything
        BorderPane everything = new BorderPane();
        everything.setTop(title);
        everything.setCenter(content);
        everything.setAlignment(title, Pos.CENTER);


        Scene scene = new Scene(everything, 800, 500);
        //scene.getStylesheets().add(getClass().getResource("css/statsbox.css").toString()); //add the stylesheet

        //set the scene to be the one displayed on the window
        window.setScene(scene);

        //load the window
        window.show();
    }
}
