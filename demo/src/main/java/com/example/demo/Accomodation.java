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
            for(Student student : studenti)
            {
                for (String preferinta : student.getPreferinte())
                {
                    if(verificaDisponibilitatePreferinta(preferinta, camine, student)==true)
                    {
                        updateRepartizareCaminPentruStudent(student.getId(), preferinta);
                        break;
                    }
                }
            }
        }
    }

    public void updateRepartizareCaminPentruStudent(int id_student, String caminRepartizat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET camin_repartizat = ? WHERE id = ?");
            preparedStatement.setString(1, caminRepartizat);
            preparedStatement.setInt(2, id_student);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a actualizat cu succes caminul repartizat al studentului.");
            } else {
                System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaDisponibilitatePreferinta(String preferinta, List<Camin> camine, Student student) {
        // Căutăm caminul în listă după nume
        for (Camin camin : camine) {
            if (camin.getNume().equals(preferinta)) {
                // Am găsit caminul, decrementăm nrLocuri si ii asociem studentului un camin
                if(student.getGen()=="fata") {
                    int nrLocuri = getNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                    if (nrLocuri > 0) {
                        decrementareNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                        return true;
                    } else {
                        return false;
                    }
                }
                else if (student.getGen()=="baiat") {
                    int nrLocuri = getNrLocuriBaietiDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                    if (nrLocuri > 0) {
                        decrementareNrLocuriBaietiDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        System.out.println("Caminul " + preferinta + " nu există în listă.");
        return false;
    }

    public int getNrLocuriBaietiDeLaOFaculatePentruUnCamin(String numeFacultate, int idCamin) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT locuri_baieti FROM facultate_camine WHERE nume_facultate =? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int nr_locuri_baieti = resultSet.getInt("locuri_baieti");
                return nr_locuri_baieti;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void decrementareNrLocuriBaietiDeLaOFaculatePentruUnCamin(String numeFacultate, int idCamin) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE facultate_camine SET locuri_baieti = locuri_baieti - 1 WHERE nume_facultate = ? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a actualizat cu succes numarul de locuri baieti.");
            } else {
                System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNrLocuriFeteDeLaOFaculatePentruUnCamin(String numeFacultate, int idCamin) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT locuri_fete FROM facultate_camine WHERE nume_facultate =? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int nr_locuri_fete = resultSet.getInt("locuri_fete");
                return nr_locuri_fete;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void decrementareNrLocuriFeteDeLaOFaculatePentruUnCamin(String numeFacultate, int idCamin) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE facultate_camine SET locuri_fete = locuri_fete - 1 WHERE nume_facultate = ? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a actualizat cu succes numarul de locuri fete.");
            } else {
                System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudentiDupaMedieDeLaFacultate(String facultate) {
        List<Student> studenti = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM studenti WHERE facultate = ? ORDER BY medie DESC");
            preparedStatement.setString(1, facultate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String gen = resultSet.getString("gen");
                String nr_matricol = resultSet.getString("nr_matricol");
                String email = resultSet.getString("email");
                String telefon = resultSet.getString("telefon");
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
                Student student = new Student(id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinte, connection);
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
