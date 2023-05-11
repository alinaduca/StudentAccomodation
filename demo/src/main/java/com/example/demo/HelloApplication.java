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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HelloApplication extends Application {
    private String firstName;
    private String lastName;
    private double textFieldWidth = 200;
    private String nrMatricol;
    private String email;
    private String telefon;
    private String facultate;
    private float medie;
    private List<String> preferinte;
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Label numeLabel = new Label("Nume: ");
        TextField numeTextField = new TextField(firstName);
        numeTextField.setPrefWidth(textFieldWidth);
        numeTextField.setMaxWidth(textFieldWidth);
        Label prenumeLabel = new Label("Prenume: ");
        TextField prenumeTextField = new TextField(lastName);
        prenumeTextField.setPrefWidth(textFieldWidth);
        prenumeTextField.setMaxWidth(textFieldWidth);
        Label nrMatricolLabel = new Label("Număr matricol: ");
        TextField nrMatricolTextField = new TextField(nrMatricol);
        nrMatricolTextField.setPrefWidth(textFieldWidth);
        nrMatricolTextField.setMaxWidth(textFieldWidth);

        Label emailLabel = new Label("Email: ");
        TextField emailTextField = new TextField(email);
        emailTextField.setPrefWidth(textFieldWidth);
        emailTextField.setMaxWidth(textFieldWidth);

        Label telefonLabel = new Label("Numar de telefon: ");
        TextField telefonTextField = new TextField(telefon);
        telefonTextField.setPrefWidth(textFieldWidth);
        telefonTextField.setMaxWidth(textFieldWidth);

        Label facultateLabel = new Label("Facultate: ");
        TextField facultateTextField = new TextField(facultate);
        facultateTextField.setPrefWidth(textFieldWidth);
        facultateTextField.setMaxWidth(textFieldWidth);

        Label medieLabel = new Label("Media pe ultimul an universitar încheiat: ");
        TextField medieTextField = new TextField(Float.toString(medie));
        medieTextField.setPrefWidth(textFieldWidth);
        medieTextField.setMaxWidth(textFieldWidth);

        String pref1 = null;
        Label preferinteLabel = new Label("Preferințe: ");
        TextField preferinteTextField = new TextField(pref1);
        preferinteTextField.setPrefWidth(textFieldWidth);
        preferinteTextField.setMaxWidth(textFieldWidth);

        Canvas canvas = new Canvas(700, 600);
        //email, telefon, facultate, medie, preferinte.
        VBox configPanel = new VBox(numeLabel, numeTextField, prenumeLabel, prenumeTextField,
                nrMatricolLabel, nrMatricolTextField, emailLabel, emailTextField,
                telefonLabel, telefonTextField, facultateLabel, facultateTextField,
                medieLabel, medieTextField, preferinteLabel, preferinteTextField
        );
        configPanel.setAlignment(Pos.CENTER);
        configPanel.setSpacing(10);
        Button submit = new Button("Aplicare");
        HBox controlPanel = new HBox(submit);
        controlPanel.setAlignment(Pos.CENTER);
        root.setCenter(configPanel);
        root.setBottom(controlPanel);

//        root.setCenter(canvas);
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
//        Connection connection = DatabaseConnection.getInstance().getConnection();
//        Accomodation accomodation = new Accomodation(connection);
//        accomodation.RepartizareStudentiInCamin();
        launch();
    }
}

//Aplicatie de tip client-server, la partea de client voi avea interfata grafica iar la partea de server voi avea partea de procesare a cerintelor studentilor.
//Un student va avea o lista de preferinte a caminelor, un nr matricol si o medie in functie de care va fi repartizat in camin.
