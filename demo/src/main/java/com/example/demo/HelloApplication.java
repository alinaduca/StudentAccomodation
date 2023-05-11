package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private String firstName;
    private String lastName;
    private double textFieldWidth = 200;
    private String nrMatricol;
    private String email;
    private String telefon;
    private boolean gen;
    private String facultate;
    private double medie;
    private List<String> preferinte;
    @Override
    public void start(Stage stage) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Accomodation accomodation = new Accomodation(connection);
        accomodation.RepartizareStudentiInCamin();

        BorderPane root = new BorderPane();
        Label numeLabel = new Label("Nume: ");
        TextField numeTextField = new TextField();
        numeTextField.setPrefWidth(textFieldWidth);
        numeTextField.setMaxWidth(textFieldWidth);
        HBox numePanel = new HBox(numeLabel, numeTextField);

        Label prenumeLabel = new Label("Prenume: ");
        TextField prenumeTextField = new TextField();
        prenumeTextField.setPrefWidth(textFieldWidth);
        prenumeTextField.setMaxWidth(textFieldWidth);
        HBox prenumePanel = new HBox(prenumeLabel, prenumeTextField);

        Label nrMatricolLabel = new Label("Număr matricol: ");
        TextField nrMatricolTextField = new TextField(nrMatricol);
        nrMatricolTextField.setPrefWidth(textFieldWidth);
        nrMatricolTextField.setMaxWidth(textFieldWidth);
        HBox nrMatricolPanel = new HBox(nrMatricolLabel, nrMatricolTextField);

        Label emailLabel = new Label("Email: ");
        TextField emailTextField = new TextField(email);
        emailTextField.setPrefWidth(textFieldWidth);
        emailTextField.setMaxWidth(textFieldWidth);
        HBox emailPanel = new HBox(emailLabel, emailTextField);

        Label telefonLabel = new Label("Număr de telefon: ");
        TextField telefonTextField = new TextField(telefon);
        telefonTextField.setPrefWidth(textFieldWidth);
        telefonTextField.setMaxWidth(textFieldWidth);
        HBox telefonPanel = new HBox(telefonLabel, telefonTextField);

        Label facultateLabel = new Label("Facultate: ");
        List<String> list = accomodation.getFacultati();
        ObservableList<String> options = FXCollections.observableArrayList(list);
        ComboBox facultateComboBox = new ComboBox(options);
        facultateComboBox.setPrefWidth(textFieldWidth);
        facultateComboBox.setMaxWidth(textFieldWidth);
        HBox facultatePanel = new HBox(facultateLabel, facultateComboBox);


        Label medieLabel = new Label("Media pe ultimul an universitar încheiat: ");
        TextField medieTextField = new TextField();
        medieTextField.setPrefWidth(50);
        medieTextField.setMaxWidth(50);
        HBox mediePanel = new HBox(medieLabel, medieTextField);

        Label preferinteLabel = new Label("Preferințe: ");
        List<Camin> listCamine = new ArrayList<>();
        List<Camin> listCamine1 = new ArrayList<>();
        List<Camin> listCamine2 = new ArrayList<>();
        List<Camin> listCamine3 = new ArrayList<>();
        List<Camin> listCamine4 = new ArrayList<>();
        ObservableList<String> optionsCamine = FXCollections.observableArrayList();
        ObservableList<String> optionsCamine1 = FXCollections.observableArrayList();
        ObservableList<String> optionsCamine2 = FXCollections.observableArrayList();
        ObservableList<String> optionsCamine3 = FXCollections.observableArrayList();
        ObservableList<String> optionsCamine4 = FXCollections.observableArrayList();
        ComboBox camineComboBox = new ComboBox<>(optionsCamine);
        camineComboBox.setPrefWidth(textFieldWidth);
        camineComboBox.setPrefWidth(textFieldWidth);
        camineComboBox.setMaxHeight(-1);
        camineComboBox.setPrefHeight(-1);
        facultateComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                facultate = t1;
                listCamine.clear();
                listCamine1.clear();
                listCamine2.clear();
                listCamine3.clear();
                listCamine4.clear();
                listCamine.addAll(accomodation.getCaminePentruFacultate(facultate));
                optionsCamine.setAll(listCamine.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
                optionsCamine1.clear();
                optionsCamine2.clear();
                optionsCamine3.clear();
                optionsCamine4.clear();
            }
        });
        Label pref1 = new Label(" 1.) ");
        HBox pref1Panel = new HBox(pref1, camineComboBox);


        ComboBox camineComboBox1 = new ComboBox<>(optionsCamine1);
        camineComboBox1.setPrefWidth(textFieldWidth);
        camineComboBox1.setPrefWidth(textFieldWidth);
        camineComboBox1.setMaxHeight(-1);
        camineComboBox1.setPrefHeight(-1);

        camineComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                listCamine1.clear();
                listCamine2.clear();
                listCamine3.clear();
                listCamine4.clear();
                listCamine1.addAll(listCamine);
                listCamine1.remove(Camin.getByName(t1));
                System.out.println(Camin.getByName(t1));
                optionsCamine1.setAll(listCamine1.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
                optionsCamine2.clear();
                optionsCamine3.clear();
                optionsCamine4.clear();
            }
        });
        ComboBox camineComboBox2 = new ComboBox<>(optionsCamine2);
        camineComboBox2.setPrefWidth(textFieldWidth);
        camineComboBox2.setPrefWidth(textFieldWidth);
        camineComboBox2.setMaxHeight(-1);
        camineComboBox2.setPrefHeight(-1);

        camineComboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                listCamine2.clear();
                listCamine3.clear();
                listCamine4.clear();
                listCamine2.addAll(listCamine);
                listCamine2.remove(Camin.getByName(t1));
                System.out.println(Camin.getByName(t1));
                optionsCamine2.setAll(listCamine1.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
                optionsCamine3.clear();
                optionsCamine4.clear();
            }
        });

        ComboBox camineComboBox3 = new ComboBox<>(optionsCamine3);
        camineComboBox3.setPrefWidth(textFieldWidth);
        camineComboBox3.setPrefWidth(textFieldWidth);
        camineComboBox3.setMaxHeight(-1);
        camineComboBox3.setPrefHeight(-1);

        camineComboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                listCamine3.clear();
                listCamine4.clear();
                listCamine3.addAll(listCamine);
                listCamine3.remove(Camin.getByName(t1));
                System.out.println(Camin.getByName(t1));
                optionsCamine3.setAll(listCamine1.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
                optionsCamine4.clear();
            }
        });

        ComboBox camineComboBox4 = new ComboBox<>(optionsCamine4);
        camineComboBox4.setPrefWidth(textFieldWidth);
        camineComboBox4.setPrefWidth(textFieldWidth);
        camineComboBox4.setMaxHeight(-1);
        camineComboBox4.setPrefHeight(-1);

        camineComboBox3.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                listCamine4.clear();
                listCamine3.addAll(listCamine);
                listCamine4.remove(Camin.getByName(t1));
                System.out.println(Camin.getByName(t1));
                optionsCamine4.setAll(listCamine1.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
            }
        });





        Label pref2 = new Label(" 2.) ");
        HBox pref2Panel = new HBox(pref2, camineComboBox1);

        Label pref3 = new Label(" 3.) ");
        HBox pref3Panel = new HBox(pref3, camineComboBox2);

        Label pref4 = new Label(" 4.) ");
        HBox pref4Panel = new HBox(pref4, camineComboBox3);

        Label pref5 = new Label(" 5.) ");
        HBox pref5Panel = new HBox(pref5, camineComboBox4);

        VBox preferinte = new VBox(pref1Panel, pref2Panel, pref3Panel, pref4Panel, pref5Panel);
        preferinte.setSpacing(10);
        HBox preferintePanel = new HBox(preferinteLabel, preferinte);

        Label genreLabel = new Label("Gen: ");
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Feminin");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("Masculin");
        rb2.setToggleGroup(group);

        VBox bifat = new VBox(rb1, rb2);
        bifat.setPadding(new Insets(0, 0, 0, 10));
        bifat.setSpacing(5);
        HBox genrePanel = new HBox(genreLabel, bifat);

        Canvas canvas = new Canvas(800, 600);
        VBox configPanel = new VBox(numePanel, prenumePanel,
                nrMatricolPanel, emailPanel,
                telefonPanel, facultatePanel,
                mediePanel, preferintePanel, genrePanel
        );
        configPanel.setPadding(new Insets(0, 0, 0, 40));
        configPanel.setAlignment(Pos.CENTER);
        configPanel.setSpacing(10);
        Button submit = new Button("Aplicare");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lastName = numeTextField.getText();
                firstName = prenumeTextField.getText();
                nrMatricol = nrMatricolTextField.getText();
                email = emailTextField.getText();
                telefon = telefonTextField.getText();
                try {
                    medie = Double.parseDouble(medieTextField.getText());
                } catch(NumberFormatException e) {
                    System.out.println("Medie incorecta");
                }
            }
        });
        HBox controlPanel = new HBox(submit);
        controlPanel.setAlignment(Pos.CENTER);
        root.setCenter(configPanel);
        root.setBottom(controlPanel);

//        root.setCenter(canvas);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}

//Aplicatie de tip client-server, la partea de client voi avea interfata grafica iar la partea de server voi avea partea de procesare a cerintelor studentilor.
//Un student va avea o lista de preferinte a caminelor, un nr matricol si o medie in functie de care va fi repartizat in camin.
