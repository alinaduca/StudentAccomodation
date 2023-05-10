package com.example.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accomodation {
    Connection connection;
    public Accomodation(String lastName, String firstName, String an, String grupa, String emailAdress, String matricol, String medie, String dataNastere, String femaleGender, String maleGender, Connection connection) {
        this.connection=connection;
    }

    public void RepartizareStudentiInCamin () {



    }

//    public (String numarMatricol) {
//        double medie = 0;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT medie FROM Studenti WHERE nrMatricol=?");
//            preparedStatement.setString(1, numarMatricol);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                medie = resultSet.getDouble("medie");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return medie;
//    }

    public double getMedieByNrMatricol(String numarMatricol) {
        double medie = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT medie FROM Studenti WHERE nrMatricol=?");
            preparedStatement.setString(1, numarMatricol);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                medie = resultSet.getDouble("medie");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medie;
    }
    public double getCapacitateCaminByNume(String numeCamin) {
        int capacitate = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT capacitateTotala FROM Camine WHERE denumire=?");
            preparedStatement.setString(1, numeCamin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                capacitate = resultSet.getInt("capacitateTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return capacitate;
    }


}
