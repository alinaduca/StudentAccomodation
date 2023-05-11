package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private String firstName;
    private String lastName;
    private String nrMatricol;
    private String email;
    private String telefon;
    private String gen;
    private String facultate;
    private String mesaj;
    private double medie;
    Camin camin1;
    Camin camin2;
    Camin camin3;
    Camin camin4;
    Camin camin5;
    private List<Camin> preferinteCamine = new ArrayList<>();

    @Override
    public void start(Stage stage) throws SQLException {
        final double textFieldWidth = 200;
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
                camin1 = Camin.getByName(t1);
                listCamine1.remove(camin1);
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
                listCamine2.addAll(listCamine1);
                camin2 = Camin.getByName(t1);
                listCamine2.remove(camin2);
                listCamine2.remove(camin1);
                optionsCamine2.setAll(listCamine2.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
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
                listCamine3.addAll(listCamine2);
                camin3 = Camin.getByName(t1);
                listCamine3.remove(camin3);
                listCamine3.remove(camin2);
                listCamine3.remove(camin1);
                optionsCamine3.setAll(listCamine3.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
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
                camin4 = Camin.getByName(t1);
                listCamine4.remove(camin1);
                listCamine4.remove(camin2);
                listCamine4.remove(camin3);
                listCamine4.remove(camin4);
                optionsCamine4.setAll(listCamine4.stream().map(camin -> camin.getNume()).collect(Collectors.toList()));
            }
        });

        camineComboBox4.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                camin5 = Camin.getByName(t1);
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
        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Feminin");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("Masculin");
        rb2.setToggleGroup(group);
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if(((RadioButton)newValue).getText().equals("Feminin")) {
                    gen = "Feminin";
                }
                else {
                    gen = "Masculin";
                }
            }
        });
        VBox bifat = new VBox(rb1, rb2);
        bifat.setPadding(new Insets(0, 0, 0, 10));
        bifat.setSpacing(5);
        HBox genrePanel = new HBox(genreLabel, bifat);

        Canvas canvas = new Canvas(700, 600);
        VBox configPanel = new VBox(numePanel, prenumePanel,
                nrMatricolPanel, emailPanel,
                telefonPanel, facultatePanel,
                mediePanel, preferintePanel, genrePanel
        );
        configPanel.setPadding(new Insets(0, 0, 0, 100));
        configPanel.setAlignment(Pos.CENTER);
        configPanel.setSpacing(10);
        Button submit = new Button("Aplicare");
        Label mesajLabel = new Label();
        VBox controlPanel = new VBox(mesajLabel, submit);
        controlPanel.setSpacing(5);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesaj = null;
                lastName = numeTextField.getText();
                firstName = prenumeTextField.getText();
                nrMatricol = nrMatricolTextField.getText();
                email = emailTextField.getText();
                telefon = telefonTextField.getText();
                if(preferinteCamine != null) {
                    preferinteCamine.clear();
                }
                if(camin1 != null) {
                    preferinteCamine.add(camin1);
                }
                if(camin2 != null && !preferinteCamine.contains(camin2)) {
                    preferinteCamine.add(camin2);
                }
                if(camin3 != null && !preferinteCamine.contains(camin3)) {
                    preferinteCamine.add(camin3);
                }
                if(camin4 != null && !preferinteCamine.contains(camin4)) {
                    preferinteCamine.add(camin4);
                }
                if(camin5 != null && !preferinteCamine.contains(camin5)) {
                    preferinteCamine.add(camin5);
                }
                try {
                    medie = Double.parseDouble(medieTextField.getText());
                } catch(NumberFormatException e) {
                    mesaj = "Introduceți o medie validă.";
                }
                if(mesaj == null) {
                    switch(valid(lastName, firstName, nrMatricol, email, telefon, facultate, medie, preferinteCamine, gen)) {
                        case 0: mesaj = null; break;
                        case 1: mesaj = "Introduceți numele."; break;
                        case 2: mesaj = "Introduceți prenumele."; break;
                        case 3: mesaj = "Introduceți numărul matricol."; break;
                        case 4: mesaj = "Introduceți un email valid."; break;
                        case 5: mesaj = "Introduceți un număr de telefon valid."; break;
                        case 6: mesaj = "Selectați o facultate."; break;
                        case 7: mesaj = "Introduceți o medie validă."; break;
                        case 8: mesaj = "Selectați cel puțin un cămin."; break;
                        case 9: mesaj = "Bifați genul."; break;
                    }
                }
                mesajLabel.setText(mesaj);
            }
        });
        controlPanel.setAlignment(Pos.CENTER);
        root.setCenter(configPanel);
        root.setBottom(controlPanel);
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }

    private int valid(String lastName, String firstName, String nrMatricol, String email, String telefon, String facultate, double medie, List<Camin> preferinte, String gen) {
        if(lastName == null || lastName.length() < 2) {
            return 1;
        }
        if(firstName == null || firstName.length() < 2) {
            return 2;
        }
        if(nrMatricol == null || nrMatricol.length() < 2) {
            return 3;
        }
        if(email == null || email.length() < 2 || !email.contains("@") || !email.contains(".")) {
            return 4;
        }
        if(telefon == null || telefon.length() != 10) {
            return 5;
        }
        for(int i = 0; i < telefon.length(); i++) {
            char c = telefon.charAt(i);
            String s = c + "";
            if(!"0123456789".contains(s)) {
                System.out.println(s + ",");
                return 5;
            }
        }
        if(facultate == null || facultate.length() < 2) {
            return 6;
        }
        if(medie < 1 || medie > 10) {
            return 7;
        }
        if(preferinte == null || preferinte.size() < 1) {
            return 8;
        }
        if(gen == null || gen.length() < 2) {
            return 9;
        }
        return 0;
    }
}

//Aplicatie de tip client-server, la partea de client voi avea interfata grafica iar la partea de server voi avea partea de procesare a cerintelor studentilor.
//Un student va avea o lista de preferinte a caminelor, un nr matricol si o medie in functie de care va fi repartizat in camin.
