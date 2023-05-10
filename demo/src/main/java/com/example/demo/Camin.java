package com.example.demo;

import java.util.Objects;

public class Camin {
    private String nume;
    private Integer capacitateTotala;
    private Integer capacitatePerCamera;
    private Integer pret;
    private Integer nrLocuriFete;
    private Integer nrLocuriBaieti;

    public Camin(String nume, Integer capacitateTotala, Integer capacitatePerCamera, Integer pret, Integer nrLocuriFete, Integer nrLocuriBaieti) {
        this.nume = nume;
        this.capacitateTotala = capacitateTotala;
        this.capacitatePerCamera = capacitatePerCamera;
        this.pret = pret;
        this.nrLocuriFete = nrLocuriFete;
        this.nrLocuriBaieti = nrLocuriBaieti;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getCapacitateTotala() {
        return capacitateTotala;
    }

    public void setCapacitateTotala(Integer capacitateTotala) {
        this.capacitateTotala = capacitateTotala;
    }

    public Integer getCapacitatePerCamera() {
        return capacitatePerCamera;
    }

    public void setCapacitatePerCamera(Integer capacitatePerCamera) {
        this.capacitatePerCamera = capacitatePerCamera;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public Integer getNrLocuriFete() {
        return nrLocuriFete;
    }

    public void setNrLocuriFete(Integer nrLocuriFete) {
        this.nrLocuriFete = nrLocuriFete;
    }

    public Integer getNrLocuriBaieti() {
        return nrLocuriBaieti;
    }

    public void setNrLocuriBaieti(Integer nrLocuriBaieti) {
        this.nrLocuriBaieti = nrLocuriBaieti;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camin camin = (Camin) o;
        return Objects.equals(nume, camin.nume) && Objects.equals(capacitateTotala, camin.capacitateTotala) && Objects.equals(capacitatePerCamera, camin.capacitatePerCamera) && Objects.equals(pret, camin.pret) && Objects.equals(nrLocuriFete, camin.nrLocuriFete) && Objects.equals(nrLocuriBaieti, camin.nrLocuriBaieti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, capacitateTotala, capacitatePerCamera, pret, nrLocuriFete, nrLocuriBaieti);
    }
}
