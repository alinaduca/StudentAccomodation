package com.example.client;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Accomodation {
    Connection connection;
    public Accomodation(Connection connection) {
        this.connection = connection;
    }

    public void RepartizareStudentiInCamin () {
        //pentru fiecare facultate
        List<String> facultati = getFacultati();
//        System.out.println("Facultati: ");
//        System.out.println(facultati.toString());
        for (String facultate : facultati)
        {
            //selectam caminele la care facultatea a primit locuri
//            System.out.println("");
//            System.out.println("Camine la " + facultate);
            List<Camin> camine = getCaminePentruFacultate(facultate);
//            System.out.println(camine.toString());
            //selectam studentii ordonati descrescator dupa medie
//            System.out.println("");
//            System.out.println("Studenti dupa medie:");
            List<Student> studenti = getStudentiDupaMedieDeLaFacultate(facultate);
//            System.out.println(studenti.toString());
            for(Student student : studenti)
            {
                for (Camin preferinta : student.getPreferinte())
                {
                    //asta inca nu da bine dar cred ca e din cauza ca trebuie sa ma adaug cate cave in baza de date, ca sunt prea putine in facultati_camine
//                    if(verificaDisponibilitatePreferinta(preferinta, camine, student)==true)
//                    {
//                        System.out.println("am actualizat caminul studentului " + student.getFirstName() + " cu valloarea " + preferinta);
//                        updateRepartizareCaminPentruStudent(student.getId(), preferinta);
//                        break;
//                    }
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
//                System.out.println("S-a actualizat cu succes caminul repartizat al studentului.");
            } else {
//                System.out.println("Nu s-a actualizat niciun rand din tabel.");
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
                if(student.getGen().equals("fata")) {
//                    System.out.println("E fata");
                    int nrLocuri = getNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
//                    System.out.println(nrLocuri);
                    if (nrLocuri > 0) {
                        decrementareNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                        return true;
                    } else {
                        return false;
                    }
                }
                else if (student.getGen().equals("baiat")) {
                    System.out.println("E baiat");
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
        //System.out.println("Caminul " + preferinta + " nu exista în lista.");
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
            while (resultSet.next()) {
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
//                System.out.println("S-a actualizat cu succes numarul de locuri fete.");
            } else {
//                System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudentiDupaMedieDeLaFacultate(String facultate) {
        List<Student> studenti = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM studenti1 WHERE facultate = ? ORDER BY medie DESC");
            preparedStatement.setString(1, facultate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String gen = resultSet.getString("gen");
                String nr_matricol = resultSet.getString("nr_matricol");
                String email = resultSet.getString("email");
                String telefon = resultSet.getString("telefon");
                double medie = resultSet.getDouble("medie");
                String preferinta1 = resultSet.getString("preferinta1");
                String preferinta2 = resultSet.getString("preferinta2");
                String preferinta3 = resultSet.getString("preferinta3");
                String preferinta4 = resultSet.getString("preferinta4");
                String preferinta5 = resultSet.getString("preferinta5");
                List<Camin> preferinte = new ArrayList<>();
                preferinte.add(Camin.getByName(preferinta1));
                preferinte.add(Camin.getByName(preferinta2));
                preferinte.add(Camin.getByName(preferinta3));
                preferinte.add(Camin.getByName(preferinta4));
                preferinte.add(Camin.getByName(preferinta5));
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM facultate_camine fc JOIN camine c ON fc.id_camin=c.id WHERE nume_facultate = ?");
            preparedStatement.setString(1, facultate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
            while (resultSet.next()) {
                String facultate = resultSet.getString("nume_facultate");
                facultati.add(facultate);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("ok");
//            e.printStackTrace();
        }
        return facultati;
    }
}
