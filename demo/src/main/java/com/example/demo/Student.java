package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class Student {
    private String firstName;
    private String lastName;
    private String nrMatricol;
    private String email;
    private String telefon;
    private String facultate;
    private String camin_repartizat;
    private Float medie;
    private List<String> preferinte;
    Connection connection;

    public Student(String firstName, String lastName, String nrMatricol, String email, String telefon, String facultate, Float medie, List<String> preferinte, Connection connection) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.telefon = telefon;
        this.facultate = facultate;
        this.medie = medie;
        this.preferinte = preferinte;
        this.connection=connection;
        this.camin_repartizat=null;
    }

    public Student() {
        preferinte = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Float getMedie() {
        return medie;
    }

    public void setMedie(Float medie) {
        this.medie = medie;
    }

    public List<String> getPreferinte() {
        return preferinte;
    }

    public void setPreferinte(List<String> preferinte) {
        this.preferinte = preferinte;
    }

//    public Student(String firstName, String lastName, String nrMatricol, String email, String telefon, Float medie, List<String> preferinte, Connection connection) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.nrMatricol = nrMatricol;
//        this.email = email;
//        this.telefon = telefon;
//        this.medie = medie;
//        this.preferinte = preferinte;
//        this.connection=connection;
//    }

}
