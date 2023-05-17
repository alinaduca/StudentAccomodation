package com.example.client;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import java.net.UnknownHostException;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import java.net.InetAddress;
import javafx.geometry.Pos;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.Socket;
import java.util.List;
import java.io.*;

public class ClientApplication extends Application {
    private int port;
    private InetAddress host;
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private String firstName;
    private String lastName;
    private String nrMatricol;
    private String email;
    private String telefon;
    private String gen;
    private String facultate;
    private String mesaj;
    private double medie;
    private String camin1;
    private String camin2;
    private String camin3;
    private String camin4;
    private String camin5;
    private List<String> preferinteCamine;

    public void init() {
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server at " + host + ":" + port);
        } catch(IOException ignored) { }
    }

    @Override
    public void start(Stage stage) {
        try {
            this.host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = 12345;
        init();
        if(socket == null) {
            try {
                socket = new Socket(host.getHostName(), port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(out == null) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stage.setTitle("StudentAccomodation");
        firstPage(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    private int valid(String lastName, String firstName, String nrMatricol, String email, String telefon, String facultate, double medie, List<String> preferinte, String gen) {
        if(lastName == null || lastName.length() < 2) {
            return 1;
        }
        if(firstName == null || firstName.length() < 2) {
            return 2;
        }
        if(nrMatricol == null || nrMatricol.length() != 12) {
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

    private void firstPage(Stage stage) {
        out.println("get-facultati");
        String inputLine = null;
        try {
            inputLine = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] parts = inputLine.split(";");
        List<String> list = new ArrayList<>();
        for(String fac : parts) {
            if(fac.contains("Facultatea")) {
                list.add(fac);
            }
        }

        BorderPane root = new BorderPane();
        Button register = new Button("Înregistrare student");
        register.setFont(Font.font(20));
        Button check = new Button("Verifică repartizare");
        check.setFont(Font.font(20));
        Button admin = new Button("Conectare administrator");
        admin.setFont(Font.font(20));

        Label mesajAllert = new Label();
        HBox allert = new HBox(mesajAllert);
        allert.setAlignment(Pos.CENTER);
        allert.setPadding(new Insets(0, 0, 20, 0));
        VBox menu = new VBox(register, check, admin);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(70);
        root.setCenter(menu);
        root.setBottom(allert);
        stage.setScene(new Scene(root, 700, 600));
        stage.show();

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!repartizareTurul1) {
                    try {
                        draw(stage, list);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    mesajAllert.setText("S-a încheiat perioada de înscriere.");
                    mesajAllert.setTextFill(Color.RED);
                }
            }
        });

        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesajAllert.setText("");
                checkRepartition(stage);
            }
        });

        admin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesajAllert.setText("");
                loginAdmin(stage);
            }
        });
    }

    private static boolean repartizareTurul1 = false;
    private static boolean repartizareTurul2 = false;

    private void checkRepartition(Stage stage) {
        BorderPane root = new BorderPane();

        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstPage(stage);
            }
        });
        Label nrMatricolLabel = new Label("Introduceți numărul matricol: ");
        TextField nrMatricolTextField = new TextField();
        Button verificaButton = new Button("Verifică");
        Label mesajNumarMatricol = new Label();
        verificaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nrMat = nrMatricolTextField.getText();
                if(nrMat == null || nrMat.length() < 1) {
                    mesajNumarMatricol.setText("Nu ai introdus numărul matricol.");
                    mesajNumarMatricol.setTextFill(Color.RED);
                }
                else if(nrMat.length() != 12) {
                    mesajNumarMatricol.setText("Numărul matricol nu este valid.");
                    mesajNumarMatricol.setTextFill(Color.RED);
                }
                else {
                    //Aici ii trimit serverului mesajul ca s-a citit un nr matricol
                    //Mesajul ar fi sub forma "student;<nr-matricol>"
                    //Aici mi se va returna un mesaj, anume daca studentul a intrat sau nu la un camin selectat.
                }
            }
        });
        HBox verifica = new HBox(verificaButton);
        verifica.setAlignment(Pos.CENTER);
        HBox nrMatPanel = new HBox(nrMatricolLabel, nrMatricolTextField);
        nrMatPanel.setAlignment(Pos.CENTER);

        VBox message = new VBox(mesajNumarMatricol);
        VBox mainPanel = new VBox(nrMatPanel, verifica, message);
        message.setAlignment(Pos.CENTER);
        Label feedbackRepartizare = new Label("Nu ai fost repartizat.");
        VBox newVbox = new VBox(feedbackRepartizare);
        newVbox.setAlignment(Pos.CENTER);
        mainPanel.setSpacing(20);
        HBox backPanel = new HBox(backButton);
        root.setTop(backPanel);
        root.setCenter(mainPanel);
        stage.setTitle("StudentAccomodation");
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }

    private void repartition(Stage stage) {
        final boolean[] extraselectionPanelAlreadyAdded = {false};
        final boolean[] saveCriteriaSelected = {false};
        BorderPane root = new BorderPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginAdmin(stage);
            }
        });

        HBox backPanel = new HBox(backButton);
        Button repartition = new Button("Repartizează (turul 1)");
        Button repartition2 = new Button("Repartizează (turul 2)");
        Label mesajLabel = new Label();
        repartition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!repartizareTurul1) {
                    out.println("repartizeaza");
                    String inputLine = null;
                    try {
                        inputLine = in.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mesajLabel.setText("Repartizarea a fost realizată cu succes.");
                    mesajLabel.setTextFill(Color.GREEN);
                    repartizareTurul1 = true;
                }
                else {
                    if(!repartizareTurul2) {
                        mesajLabel.setText("Repartizarea pentru turul 1 a fost realizată, repartizați pentru turul 2.");
                        mesajLabel.setTextFill(Color.RED);
                    }
                    else {
                        mesajLabel.setText("Repartizările au fost efectuate.");
                        mesajLabel.setTextFill(Color.RED);
                    }
                }
            }
        });

        repartition2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!repartizareTurul1) {
                    mesajLabel.setText("Nu ați efectuat repartizarea pentru turul 1.");
                    mesajLabel.setTextFill(Color.RED);
                }
                else {
                    if(!repartizareTurul2) {
                        mesajLabel.setText("Repartizarea a fost realizată cu succes.");
                        mesajLabel.setTextFill(Color.GREEN);
                        repartizareTurul2 = true;
                    }
                    else {
                        mesajLabel.setText("Repartizările au fost efectuate.");
                        mesajLabel.setTextFill(Color.RED);
                    }
                }
            }
        });

        Label criteriuLabel = new Label("Alege un criteriu de salvare a listei: ");
        List list = new ArrayList<>(List.of("Facultăți", "Cămine"));
        ObservableList<String> options = FXCollections.observableArrayList(list);
        ComboBox criterii = new ComboBox(options);
        HBox savePanel = new HBox(criteriuLabel, criterii);

        Label criteriuAlesLabel = new Label();
        List list1 = new ArrayList<>();
        ObservableList<String> options1 = FXCollections.observableArrayList(list);
        ComboBox criterii1 = new ComboBox(options1);

        HBox selectieSuplimentara = new HBox(criteriuAlesLabel, criterii1);
        selectieSuplimentara.setAlignment(Pos.CENTER);
        selectieSuplimentara.setPadding(new Insets(10, 0, 0, 0));
        VBox campSelectii = new VBox(savePanel);
        criterii.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                mesajLabel.setText("");
                saveCriteriaSelected[0] = false;
                if(t1.equals("Facultăți")) {
                    out.println("get-facultati");
                    String inputLine = null;
                    try {
                        inputLine = in.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] parts = inputLine.split(";");
                    list1.clear();
                    for(String fac : parts) {
                        if(fac.contains("Facultatea")) {
                            list1.add(fac);
                        }
                    }
                    options1.setAll(list1);
                    criteriuAlesLabel.setText("Alege facultatea: ");
                    if(!extraselectionPanelAlreadyAdded[0]) {
                        campSelectii.getChildren().add(selectieSuplimentara);
                        extraselectionPanelAlreadyAdded[0] = true;
                    }
                }
                else {
                    out.println("get-camine");
                    String inputLine = null;
                    try {
                        inputLine = in.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(inputLine.contains("Facultatea")) {
                        out.println("get-camine");
                        try {
                            inputLine = in.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(inputLine);
                    String[] parts = inputLine.split(";");
                    list1.clear();
                    for(String camin : parts) {
                        if(camin.contains("C") || camin.contains("Aka") || camin.contains("Gau")) {
                            list1.add(camin);
                        }
                    }
                    options1.setAll(list1);
                    criteriuAlesLabel.setText("Alege căminul: ");
                    if(!extraselectionPanelAlreadyAdded[0]) {
                        campSelectii.getChildren().add(selectieSuplimentara);
                        extraselectionPanelAlreadyAdded[0] = true;
                    }
                }
            }
        });

        criterii1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object t, Object t1) {
                saveCriteriaSelected[0] = true;
            }
        });

        Button saveList = new Button("Salvează listă");
        saveList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesajLabel.setText("");
                if(!saveCriteriaSelected[0]) {
                    mesajLabel.setText("Completați criteriile de realizare a listei.");
                    mesajLabel.setTextFill(Color.RED);
                }
                else {
                    mesajLabel.setText("Lista a fost salvată cu succes.");
                    mesajLabel.setTextFill(Color.GREEN);
                }
            }
        });

        savePanel.setAlignment(Pos.CENTER);

        VBox mainPanel = new VBox(repartition, repartition2, campSelectii, saveList);
        mainPanel.setSpacing(10);
        mainPanel.setAlignment(Pos.CENTER);
        HBox bottomPanel = new HBox(mesajLabel);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(20);
        bottomPanel.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(backPanel);
        root.setBottom(bottomPanel);
        root.setCenter(mainPanel);
        stage.setTitle("StudentAccomodation");
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }

    private void loginAdmin(Stage stage) {
        BorderPane root = new BorderPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstPage(stage);
            }
        });

        HBox backPanel = new HBox(backButton);
        Label usernameLabel = new Label("Nume de utilizator: ");
        Label passwordLabel = new Label("Parolă: ");
        VBox labelArea = new VBox(usernameLabel, passwordLabel);
        labelArea.setAlignment(Pos.CENTER_RIGHT);
        labelArea.setSpacing(15);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        VBox textArea = new VBox(usernameField, passwordField);
        textArea.setAlignment(Pos.CENTER_LEFT);
        textArea.setSpacing(10);

        HBox loginPanel = new HBox(labelArea, textArea);
        loginPanel.setAlignment(Pos.CENTER);

        Button connect = new Button("Autentificare");
        Label mesajLabel = new Label();
        VBox connectLabel = new VBox(connect, mesajLabel);
        connectLabel.setAlignment(Pos.CENTER);
        connectLabel.setSpacing(10);

        VBox connectPanel = new VBox(loginPanel, connectLabel);
        connectPanel.setSpacing(50);
        connectPanel.setAlignment(Pos.CENTER);

        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(username.equals("admin") && password.equals("admin")) {
                    repartition(stage);
                }
                else {
                    if(!username.equals("admin")) {
                        mesajLabel.setText("Cont inexistent");
                        mesajLabel.setTextFill(Color.RED);
                    }
                    else {
                        mesajLabel.setText("Parolă incorectă");
                        mesajLabel.setTextFill(Color.RED);
                    }
                }
            }
        });
        root.setTop(backPanel);
        root.setCenter(connectPanel);
        stage.setTitle("StudentAccomodation");
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }

    private void draw(Stage stage, List<String> list) throws IOException {
        firstName = null;
        lastName = null;
        nrMatricol = null;
        email = null;
        telefon = null;
        gen = null;
        facultate = null;
        medie = 0;
        camin1 = null;
        camin2 = null;
        camin3 = null;
        camin4 = null;
        camin5 = null;
        preferinteCamine = new ArrayList<>();
        final double textFieldWidth = 200;
        BorderPane root = new BorderPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstPage(stage);
            }
        });

        HBox backPanel = new HBox(backButton);

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
        List<String> listCamine = new ArrayList<>();
        List<String> listCamine1 = new ArrayList<>();
        List<String> listCamine2 = new ArrayList<>();
        List<String> listCamine3 = new ArrayList<>();
        List<String> listCamine4 = new ArrayList<>();
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
                out.println("get-camine-pentru-facultate:" + facultate);
                List<String> list1 = new ArrayList<>();
                String inputLine1;
                try {
                    inputLine1 = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] parts = inputLine1.split(";");
                for(String camin : parts) {
                    if(!camin.contains("Facultatea") && (camin.contains("C") || camin.contains("Aka") || camin.contains("Gau"))) {
                        list1.add(camin);
                    }
                }
                listCamine.addAll(list1);
                optionsCamine.setAll(listCamine);
                optionsCamine1.clear();
                optionsCamine2.clear();
                optionsCamine3.clear();
                optionsCamine4.clear();
                preferinteCamine = new ArrayList<>();
                camin1 = null;
                camin2 = null;
                camin3 = null;
                camin4 = null;
                camin5 = null;
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
                camin1 = t1;
                listCamine1.remove(camin1);
                optionsCamine1.setAll(listCamine1);
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
                camin2 = t1;
                listCamine2.remove(camin2);
                listCamine2.remove(camin1);
                optionsCamine2.setAll(listCamine2);
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
                camin3 = t1;
                listCamine3.remove(camin3);
                listCamine3.remove(camin2);
                listCamine3.remove(camin1);
                optionsCamine3.setAll(listCamine3);
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
                listCamine4.addAll(listCamine);
                camin4 = t1;
                listCamine4.remove(camin1);
                listCamine4.remove(camin2);
                listCamine4.remove(camin3);
                listCamine4.remove(camin4);
                optionsCamine4.setAll(listCamine4);
            }
        });

        camineComboBox4.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                camin5 = t1;
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
                String med = "0";
                try {
                    med = medieTextField.getText();
                    medie = Double.parseDouble(med);
                } catch(NumberFormatException e) {
                    mesaj = "Introduceți o medie validă.";
                }
                if(mesaj == null) {
                    switch(valid(lastName, firstName, nrMatricol, email, telefon, facultate, medie, preferinteCamine, gen)) {
                        case 0: mesaj = null;
//                                Student s = new Student(lastName, firstName, nrMatricol, email, telefon, facultate, medie, preferinteCamine, gen);
                                String studentNou = "insert-student:" + lastName + ":" + firstName + ":" + nrMatricol + ":" + email + ":" + telefon + ":"
                                        + facultate + ":" + med +  ":" + camin1 + ":" + camin2 + ":" + camin3 + ":" + camin4 + ":" + camin5 + ":" + gen;
                                out.println(studentNou);
                                firstPage(stage);
                                break;
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
                mesajLabel.setTextFill(Color.RED);
            }
        });
        controlPanel.setAlignment(Pos.CENTER);
        root.setTop(backPanel);
        root.setCenter(configPanel);
        root.setBottom(controlPanel);
        stage.setTitle("StudentAccomodation");
        stage.setScene(new Scene(root, 700, 600));
        stage.show();
    }
}

//Aplicatie de tip client-server, la partea de client voi avea interfata grafica iar la partea de server voi avea partea de procesare a cerintelor studentilor.
//Un student va avea o lista de preferinte a caminelor, un nr matricol si o medie in functie de care va fi repartizat in camin.
