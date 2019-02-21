package com.zaxxon.ui;

import com.zaxxon.client.MainGame;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 575;
	
    Button start;
    Button audio;
    Button help;


    //a method that makes the main menu scene
    public Scene makeMainMenu(MainGame mainGame, Stage window) {

        //******************************GRIDPANE 1***********************************

        //make a gridpane for the bg, buttons and logo
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(20, 20, 20, 90));
        grid1.setVgap(12); //set the gap between cells vertically and horizontally
        grid1.setHgap(20);

        grid1.setLayoutX(50);

        //add column contraints to ensure buttons are in the middle of the cell
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHalignment(HPos.CENTER); //set alignment to center
        grid1.getColumnConstraints().add(colConstraints);

        //******************************BUTTONS**********************************

        //START BUTTON

        start = new Button();
        start.setOnAction(e -> {
        	//mainGame.start(window);
        	//window.setScene(mainGame.getRenderedScene());
            ArityPopup.display(window, mainGame.getRenderedScene(), mainGame);
        });
        grid1.setConstraints(start, 0, 3);
        //load the start button text
        Image startText = new Image(getClass().getResource("img/start.png").toString());
        ImageView startView = new ImageView(startText); //make an imageview for the start button's text
        start.setGraphic(startView); //add the image to the button


        //AUDIO BUTTON

        audio = new Button();
        audio.setOnAction(e -> System.out.println("Opening audio popup..."));
        grid1.setConstraints(audio, 0, 4);
        //load the audio button text
        Image audioText = new Image(getClass().getResource("img/audio.png").toString());
        ImageView audioView = new ImageView(audioText); //make image view for audio button's text
        audio.setGraphic(audioView); //add image to button


        //HELP BUTTON

        help = new Button();
        help.setOnAction(e-> System.out.println("Opening help screen..."));
        grid1.setConstraints(help, 0, 5);
        //load the help button's text
        Image helpText = new Image(getClass().getResource("img/help.png").toString());
        ImageView helpView = new ImageView(helpText); //make image view for help button's text
        help.setGraphic(helpView); //add image to button



        //**************************LOGO****************************
        //load the logo image
        Image logo = new Image(getClass().getResource("img/cubefacelogo.png").toString());

        //set the imageview
        ImageView logoView = new ImageView(logo);

        //set the position of the image
        logoView.setX(50);
        logoView.setY(25);

        //set height/width of image
        //logoView.setFitHeight(455);
        logoView.setFitWidth(450);

        //preseve ratio of image
        logoView.setPreserveRatio(true);

        //****************************GRIDPANES AND SCENE************************************

        //add all buttons to grid1
        grid1.getChildren().addAll(start, audio, help);
        grid1.getChildren().add(logoView);


        //rootPane.getChildren().add(grid1);

        /*Scene mainmenu = new Scene(rootPane, 600, 575);
        mainmenu.getStylesheets().add(getClass().getResource("ms.css").toString()); //add the stylesheet*/

        //******************************GRIDPANE 2***********************************

        //make a gridpane for the zombie and cubeface images
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(20, 20, 20, 20));
        grid2.setVgap(100); //set the gap between cells vertically and horizontally
        grid2.setHgap(300);


        //add column contraints to ensure buttons are in the middle of the cell
        grid2.getColumnConstraints().add(colConstraints);

        //CUBEFACE IMAGE

        //load the cubeface image
        Image cubeface = new Image(getClass().getResource("img/cubeface.png").toString());

        //set the imageview
        ImageView cubefaceView = new ImageView(cubeface);

        //set the position of the image + preserve ratio
        cubefaceView.setX(20);
        cubefaceView.setY(20);
        //set size of image
        cubefaceView.setFitWidth(150);

        cubefaceView.setPreserveRatio(true);

        grid2.setConstraints(cubefaceView, 0, 1);
        grid2.getChildren().add(cubefaceView);

        //ZOMBIE IMAGE

        //load the zombie image
        Image zombie = new Image(getClass().getResource("img/zombie.png").toString());

        //set the imageview
        ImageView zombieView = new ImageView(zombie);

        //set the position of the image + preserve ratio
        zombieView.setX(20);
        zombieView.setY(20);
        //set size of image
        zombieView.setFitWidth(150);

        zombieView.setPreserveRatio(true);

        grid2.setConstraints(zombieView, 1, 1);
        grid2.getChildren().add(zombieView);



        //********************************ROOTPANE**************************************
        //make a rootpane
        GridPane rootPane = new GridPane();
        Scene mainmenu = new Scene(rootPane, WIDTH, HEIGHT);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().addAll(grid2, grid1); //add both gridpanes

        mainmenu.getStylesheets().add(getClass().getResource("ms.css").toString()); //add the stylesheet


        return mainmenu;
    }

}
