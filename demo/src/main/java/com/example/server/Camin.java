package com.example.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Camin {
    private Integer id;
    private String nume;
    private Integer capacitatePerCamera;
    private Integer pret;
    private Integer nrCamereFete;
    private Integer nrCamereBaieti;
    private static List<Camin> camine = new ArrayList<>();

    public Camin(Integer id, String nume, Integer capacitatePerCamera, Integer pret, Integer nrCamereFete, Integer nrCamereBaieti) {
        this.id=id;
        this.nume = nume;
        this.capacitatePerCamera = capacitatePerCamera;
        this.pret = pret;
        this.nrCamereFete = nrCamereFete;
        this.nrCamereBaieti = nrCamereBaieti;
        camine.add(this);
    }
    public int getId () {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
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

    public Integer getNrCamereFete() {
        return nrCamereFete;
    }

    public void setNrCamereFete(Integer nrCamereFete) {
        this.nrCamereFete = nrCamereFete;
    }

    public Integer getNrCamereBaieti() {
        return nrCamereBaieti;
    }

    public void setNrCamereBaieti(Integer nrCamereBaieti) {
        this.nrCamereBaieti = nrCamereBaieti;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camin camin = (Camin) o;
        return Objects.equals(id, camin.id) && Objects.equals(nume, camin.nume) && Objects.equals(capacitatePerCamera, camin.capacitatePerCamera) && Objects.equals(pret, camin.pret) && Objects.equals(nrCamereFete, camin.nrCamereFete) && Objects.equals(nrCamereBaieti, camin.nrCamereBaieti);
    }

    @Override
    public int hashCode() {
        //cred ca trebuie si id aici
        return Objects.hash(nume, capacitatePerCamera, pret, nrCamereFete, nrCamereBaieti);
    }

    public static Camin getByName(String name) {
        for(Camin c : camine) {
            if(c.getNume().equals(name)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nume + "id: " + id;
    }
}
