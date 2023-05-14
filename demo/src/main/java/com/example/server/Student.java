package com.example.server;

import com.example.server.Camin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String gen;
    private String nrMatricol;
    private String email;
    private String telefon;
    private String facultate;
    private String camin_repartizat;
    private double medie;
    private List<String> preferinte;
    Connection connection;

    public Student(String lastName, String firstName, String nrMatricol, String email, String telefon, String facultate, double medie, List<String> preferinte, String gen) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gen = gen;
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.telefon = telefon;
        this.facultate = facultate;
        this.medie = medie;
        this.preferinte = preferinte;
//        this.connection = connection;
        this.camin_repartizat = null;
    }

    public Student(int id, String firstName, String lastName, String gen, String nrMatricol, String email, String telefon, String facultate, double medie, List<String> preferinte, Connection connection) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gen = gen;
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

    public int getId () {
        return id;
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

    public String getGen() {
        return gen;
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

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public List<String> getPreferinte() {
        return preferinte;
    }

    public void setPreferinte(List<String> preferinte) {
        this.preferinte = preferinte;
    }

//    public Student(String firstName, String lastName, String nrMatricol, String email, String telefon, double medie, List<String> preferinte, Connection connection) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.nrMatricol = nrMatricol;
//        this.email = email;
//        this.telefon = telefon;
//        this.medie = medie;
//        this.preferinte = preferinte;
//        this.connection = connection;
//    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + nrMatricol;
    }
}
