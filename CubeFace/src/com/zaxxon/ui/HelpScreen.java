package com.zaxxon.ui;

import com.zaxxon.ui.tools.Toolbox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Help screen class
 * Builds the help screen and adds all information to it
 * @author Megan
 */
public class HelpScreen {

    public static Scene HelpScreen;

    /**
     * Makes the help screen, adds all images, text to the layouts and returns said screen
     * @param primaryStage
     * @param mainmenu
     * @return
     */
    public static Scene makeHelpScreen(Stage primaryStage, Scene mainmenu) {


        //load fonts
        Font.loadFont(HelpScreen.class.getResourceAsStream("VCR_OSD_MONO.ttf"), 9);
        Font.loadFont(HelpScreen.class.getResourceAsStream("Cube.ttf"), 9);

       // primaryStage.initStyle(StageStyle.TRANSPARENT);

        //make a title for the screen
        Label title = new Label("GAME CONTROLS");
        title.setPadding(new Insets(20, 0, 0, 0));
        title.setId("title");




        //make a movement label
        Label movement = new Label("MOVEMENT:");
        movement.setPadding(new Insets(0, 0, 15, 0));
        movement.setId("heading");

        //load the wasd image
        Image wasd = new Image(HelpScreen.class.getResource("img/wasd.png").toString());
        ImageView wasdView = new ImageView(wasd);


        //make a shooting label
        Label shoot = new Label("SHOOT:");
        shoot.setPadding(new Insets(0, 0, 15, 0));
        shoot.setId("heading");

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
        changeWeapon.setId("heading");

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
        content.setPadding(new Insets(0, 0, 120, 0));


        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 4, "Help");
        toolbox.setId("toolbox");

        //add a back button to the corner
        Button back = new Button();
        back.setPadding(new Insets(10));
        Image backImg = new Image(HelpScreen.class.getResource("img/back.png").toString()); //load back image
        ImageView backview = new ImageView(backImg);
        backview.setFitWidth(20);
        backview.setPreserveRatio(true);
        back.setGraphic(backview);
        back.setId("back");
        back.setOnAction(e-> primaryStage.setScene(mainmenu));

        //borderpane for all content
        BorderPane everything = new BorderPane();
        everything.setLeft(back);
        everything.setTop(toolbox);
        everything.setCenter(title);
        everything.setBottom(content);
        everything.setAlignment(title, Pos.CENTER);






        Scene helpScreen = new Scene(everything, MainMenu.WIDTH, MainMenu.HEIGHT);
        helpScreen.getStylesheets().add(HelpScreen.class.getResource("css/helpscreen.css").toString()); //add the stylesheet


        return  helpScreen;
    }







}
