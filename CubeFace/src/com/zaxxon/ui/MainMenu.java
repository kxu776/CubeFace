package com.zaxxon.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        //load cubeface logo

        //make a group








        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        root.getStylesheets().add(getClass().getResource("ms.css").toString());

        //create a window for the ui (a stage)
        stage.setTitle("CubeFace");
        stage.setScene(new Scene(root, 600, 575));


        //Load the logo image
        //Create the image
        Image logo = new Image(new FileInputStream("C:\\Users\\Megan\\git\\zaxxon\\CubeFace\\src\\com\\zaxxon\\ui\\cubefacelogo.png"));

        //set the image view
        ImageView imageView = new ImageView(logo);

        //set the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //create a group for the image
        Group logoGroup = new Group(imageView);


        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
