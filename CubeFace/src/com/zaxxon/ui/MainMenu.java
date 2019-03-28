package com.zaxxon.ui;

import com.zaxxon.client.MainGame;
import com.zaxxon.sound.MusicPlayer;

import com.zaxxon.ui.popups.ModePopup;
import com.zaxxon.ui.tools.Toolbox;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * MainMenu class - the first scene displayed when the application is run
 * @author Megan
 */
public class MainMenu {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static MusicPlayer music = new MusicPlayer("/mainmenu/mm.wav");
	public static Scene mainmenu;
	
    Button start;
    Button audio;
    Button help;


    /**
     * a method that makes the main menu scene
     * creates all buttons, images and text that link to other interfaces
     * @param primaryStage
     * @return
     */
    public Scene makeMainMenu(Stage primaryStage) {

        //make sure stage is not resizeable
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(600);

        double[] xOffset = {0};
        double[] yOffset = {0};

        //stage settings
        primaryStage.initStyle(StageStyle.TRANSPARENT); //remove automatic formatting for the stage
        primaryStage.setResizable(false);


        //******************************GRIDPANE 1***********************************

        //make a gridpane for the bg, buttons and logo
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(20, 20, 20, 90));
        grid1.setVgap(12); //set the gap between cells vertically and horizontally
        grid1.setHgap(20);

        //add column contraints
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHalignment(HPos.CENTER); //set alignment to center
        grid1.getColumnConstraints().add(colConstraints);

        //******************************BUTTONS**********************************

        //START BUTTON

        start = new Button("START");
        start.setOnAction(e -> {
            ModePopup.display(primaryStage, MainGame.getRenderedScene());
        });
        grid1.setConstraints(start, 0, 3);




        //AUDIO BUTTON

        audio = new Button("AUDIO");
        audio.setOnAction(e -> {
       
        MainGame.muted = (MainGame.muted)? false : true;
		
		if (MainGame.muted) {
			
			music.stop();
		}
		else {
			
			music.play();
		}
        });
        
        grid1.setConstraints(audio, 0, 4);
        
        music.play();
        MainGame.muted = false;



        //HELP BUTTON

        help = new Button("HELP");
        help.setOnAction(e->

                primaryStage.setScene(HelpScreen.makeHelpScreen(primaryStage, mainmenu)));
        grid1.setConstraints(help, 0, 5);




        //**************************LOGO****************************
        //load the logo image
        Image logo = new Image(getClass().getResource("img/cubefacelogo.png").toString());
        ImageView logoView = new ImageView(logo);

        //set the position of the image
        logoView.setX(50);
        logoView.setY(25);

        //set height/width of image
        logoView.setFitWidth(450);
        logoView.setPreserveRatio(true);

        //****************************GRIDPANES AND SCENE************************************

        //add all buttons to grid1
        grid1.getChildren().addAll(start, audio, help);
        grid1.getChildren().add(logoView);


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
        Image cubeface = new Image(getClass().getResource("img/cubeface.gif").toString());

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
        Image zombie = new Image(getClass().getResource("img/zombie.gif").toString());

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

        //make a toolbox
        AnchorPane toolbox = new Toolbox().toolbar(primaryStage, 2, "CubeFace");
        toolbox.setId("toolbox");


        //********************************ROOTPANE**************************************
        //make a root gridpane for the content
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().addAll(grid2, grid1); //add both gridpanes

        //make a borderpane for everything
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbox);
        borderPane.setCenter(rootPane);

        //make a rectangle and set clipping
        Rectangle rect = new Rectangle(WIDTH, HEIGHT);
        rect.setArcHeight(9.0);
        rect.setArcWidth(9.0);
        borderPane.setClip(rect);

        mainmenu = new Scene(borderPane, WIDTH, HEIGHT);
        mainmenu.setFill(Color.TRANSPARENT);


        //make the stage movable
        mainmenu.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });

        mainmenu.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset[0]);
                primaryStage.setY(event.getScreenY() - yOffset[0]);
            }
        });

        //add the css file
        mainmenu.getStylesheets().add(getClass().getResource("css/mainmenu.css").toString()); //add the stylesheet


        return mainmenu;
    }
}
