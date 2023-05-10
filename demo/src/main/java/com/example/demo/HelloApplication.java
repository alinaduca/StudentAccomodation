package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private int numDots = 6;
    private double lineProbability = 1.0;
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
        BorderPane root = new BorderPane();
        Label numDotsLabel = new Label("Nume: ");
        TextField numDotsTextField = new TextField(Integer.toString(numDots));
        Label numLinesLabel = new Label("   Prenume: ");
        TextField numLinesTextField = new TextField(Double.toString(lineProbability));
//        Button newGameButton = new Button("New Game");
        Canvas canvas = new Canvas(700, 300);
        HBox configPanel = new HBox(numDotsLabel, numDotsTextField, numLinesLabel, numLinesTextField);
        configPanel.setAlignment(Pos.CENTER);
        root.setTop(configPanel);
        root.setCenter(canvas);
        stage.setScene(new Scene(root, 700, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//Aplicatie de tip client-server, la partea de client voi avea interfata grafica iar la partea de server voi avea partea de procesare a cerintelor studentilor.
//Un student va avea o lista de preferinte a caminelor, un nr matricol si o medie in functie de care va fi repartizat in camin.
