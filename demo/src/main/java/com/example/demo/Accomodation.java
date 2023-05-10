package com.example.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Accomodation {
    Connection connection;
    public Accomodation(String lastName, String firstName, String an, String grupa, String emailAdress, String matricol, String medie, String dataNastere, String femaleGender, String maleGender, Connection connection) {
        this.connection=connection;
    }

    public void RepartizareStudentiInCamin () {
        //pentru fiecare facultate
        List<String> facultati = getFacultati();
        for (String facultate : facultati)
        {
            //selectam caminele la care facultatea a primit locuri
            List<Camin> camine = getCaminePentruFacultate(facultate);
            //selectam studentii ordonati descrescator dupa medie
            List<Student> studenti = getStudentiDupaMedieDeLaFacultate(facultate);
            
        }
    }


    public List<Student> getStudentiDupaMedieDeLaFacultate(String facultate) {
        List<Student> studenti = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM studenti WHERE facultate = ? ORDER BY medie DESC");
            preparedStatement.setString(1, facultate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String nr_matricol = resultSet.getString("nr_matricol");
                String email = resultSet.getString("email");
                String telefon = resultSet.getString("telefon");
                String facultate_student = resultSet.getString("facultate");
                Float medie = resultSet.getFloat("medie");
                String preferinta1 = resultSet.getString("preferinta1");
                String preferinta2 = resultSet.getString("preferinta2");
                String preferinta3 = resultSet.getString("preferinta3");
                String preferinta4 = resultSet.getString("preferinta4");
                String preferinta5 = resultSet.getString("preferinta5");
                List<String> preferinte = new ArrayList<>();
                preferinte.add(preferinta1);
                preferinte.add(preferinta2);
                preferinte.add(preferinta3);
                preferinte.add(preferinta4);
                preferinte.add(preferinta5);
                Student student = new Student(nume, prenume, nr_matricol, email, telefon, facultate, medie, preferinte, connection);
                studenti.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studenti;
    }

    public List<Camin> getCaminePentruFacultate(String facultate) {
        List<Camin> camine = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nume FROM facultate_camine fc JOIN camine c ON fc.id_camin=c.id WHERE nume_facultate = ?");
            preparedStatement.setString(1, facultate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int capacitatePerCamera = resultSet.getInt("capacitate_per_camera");
                int pret = resultSet.getInt("pret");
                int nrCamereFete = resultSet.getInt("nr_camere_fete");
                int nrCamereBaieti = resultSet.getInt("nr_camere_baieti");
                Camin camin =new Camin(id, nume, capacitatePerCamera, pret, nrCamereFete, nrCamereBaieti);
                camine.add(camin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return camine;
    }

    public List<String> getFacultati() {
        List<String> facultati = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT nume_facultate FROM facultate_camine");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String facultate = resultSet.getString("nume_facultate");
                facultati.add(facultate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultati;
    }


}
