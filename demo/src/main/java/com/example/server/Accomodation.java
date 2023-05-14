package com.example.server;

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
        for (String facultate : facultati) {
//            System.out.println("");
//            System.out.println("Camine la " + facultate);
            List<Camin> camine = getCaminePentruFacultate(facultate);
//            System.out.println(camine.toString());
            //selectam studentii ordonati descrescator dupa medie
//            System.out.println("");
//            System.out.println("Studenti dupa medie:");
            List<Student> studenti = getStudentiDupaMedieDeLaFacultate(facultate);
//            System.out.println(studenti.toString());
            for (Student student : studenti) {
                if(NuERepartizat(student.getId())==true) {
//                    System.out.println(student.toString());
                    for (String preferinta : student.getPreferinte()) {
//                        System.out.println(preferinta);
                        if (verificaDisponibilitatePreferinta(preferinta, camine, student) == true) {
//                            System.out.println("am actualizat caminul studentului " + student.getFirstName() + " cu valloarea " + preferinta);
                            updateRepartizareCaminPentruStudent(student.getId(), preferinta);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void updateRepartizareCaminPentruStudent ( int id_student, String caminRepartizat){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE studenti1 SET camin_repartizat = ? WHERE id = ?");
            preparedStatement.setString(1, caminRepartizat);
            preparedStatement.setInt(2, id_student);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a actualizat cu succes caminul repartizat al studentului.");
            }
            else {
              System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verificaDisponibilitatePreferinta (String preferinta, List <Camin> camine, Student student){
        // Cautam caminul în lista dupa nume
        for (Camin camin : camine) {
            if (camin.getNume().equals(preferinta)) {
                // Am gasit caminul, decrementam nrLocuri si ii asociem studentului un camin
                if (student.getGen().equals("fata")) {
                    int nrLocuri = getNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                    if (nrLocuri > 0) {
                        decrementareNrLocuriFeteDeLaOFaculatePentruUnCamin(student.getFacultate(), camin.getId());
                        return true;
                    } else {
                        return false;
                    }
                } else if (student.getGen().equals("baiat")) {
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
        return false;
    }

    public int getNrLocuriBaietiDeLaOFaculatePentruUnCamin (String numeFacultate,int idCamin){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT locuri_baieti FROM facultate_camine WHERE nume_facultate =? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int nr_locuri_baieti = resultSet.getInt("locuri_baieti");
                return nr_locuri_baieti;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void decrementareNrLocuriBaietiDeLaOFaculatePentruUnCamin (String numeFacultate,int idCamin){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE facultate_camine SET locuri_baieti = locuri_baieti - 1 WHERE nume_facultate = ? AND id_camin = ?");
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
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNrLocuriFeteDeLaOFaculatePentruUnCamin (String numeFacultate,int idCamin){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            System.out.println("facultatea " + numeFacultate + " la caminul " + idCamin);
            preparedStatement = connection.prepareStatement("SELECT locuri_fete FROM facultate_camine WHERE nume_facultate = ? AND id_camin = ?");
            preparedStatement.setString(1, numeFacultate);
            preparedStatement.setInt(2, idCamin);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int nr_locuri_fete = resultSet.getInt("locuri_fete");
//                System.out.println("Locuri fete la facultatea " + numeFacultate + " la caminul " + idCamin + " : " + nr_locuri_fete);
                return nr_locuri_fete;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void decrementareNrLocuriFeteDeLaOFaculatePentruUnCamin (String numeFacultate,int idCamin){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE facultate_camine SET locuri_fete = locuri_fete - 1 WHERE nume_facultate = ? AND id_camin = ?");
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
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Student> getStudentiDupaMedieDeLaFacultate (String facultate){
        List<Student> studenti = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM studenti1 WHERE facultate = ? ORDER BY medie DESC");
            preparedStatement.setString(1, facultate);
            resultSet = preparedStatement.executeQuery();
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
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return studenti;
    }

    public List<Camin> getCaminePentruFacultate (String facultate){
        List<Camin> camine = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM facultate_camine fc JOIN camine c ON fc.id_camin=c.id WHERE nume_facultate = ?");
            preparedStatement.setString(1, facultate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_camin");
                String nume = resultSet.getString("nume");
                int capacitatePerCamera = resultSet.getInt("capacitate_per_camera");
                int pret = resultSet.getInt("pret");
                int nrCamereFete = resultSet.getInt("nr_camere_fete");
                int nrCamereBaieti = resultSet.getInt("nr_camere_baieti");
                Camin camin = new Camin(id, nume, capacitatePerCamera, pret, nrCamereFete, nrCamereBaieti);
                camine.add(camin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return camine;
    }

    public List<String> getFacultati () {
        List<String> facultati = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT DISTINCT nume_facultate FROM facultate_camine");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String facultate = resultSet.getString("nume_facultate");
                facultati.add(facultate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return facultati;
    }

    boolean NuERepartizat (int id_student) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT camin_repartizat FROM studenti1 WHERE id = ?");
            preparedStatement.setInt(1, id_student);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String camin = resultSet.getString("camin_repartizat");
                if (camin!=null) return false;
                else return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void Repartizare2StudentiInCamin () {
        //pentru fiecare facultate
        List<String> facultati = getFacultati();
//        System.out.println("Facultati: ");
//        System.out.println(facultati.toString());
        for (String facultate : facultati) {
            //selectam caminele la care facultatea a primit locuri
//            System.out.println("");
//            System.out.println("Camine la " + facultate);
            List<Camin> camine = getCaminePentruFacultate(facultate);
//            System.out.println(camine.toString());
            //selectam studentii ordonati descrescator dupa medie
//            System.out.println("");
//            System.out.println("Studenti care au cerut repartizare dupa medie:");
            List<Student> studenti = getStudentiDupaMedieDeLaFacultateInSesiunea2(facultate);
//            System.out.println(studenti.toString());
            for (Student student : studenti) {
                if(NuERepartizat(student.getId())==true) {
//                    System.out.println(student.toString());
                    for (String preferinta : student.getPreferinte()) {
//                        System.out.println(preferinta);
                        if (verificaDisponibilitatePreferinta(preferinta, camine, student) == true) {
//                            System.out.println("am actualizat caminul studentului " + student.getFirstName() + " cu valloarea " + preferinta);
                            updateRepartizareCaminPentruStudent(student.getId(), preferinta);
                            break;
                        }
                    }
                }
            }
        }
        SetAllReinscrisCuNull();
    }

    public List<Student> getStudentiDupaMedieDeLaFacultateInSesiunea2 (String facultate){
        List<Student> studenti = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM studenti1 WHERE facultate = ? AND reinscris_la_camin = 1 ORDER BY medie DESC");
            preparedStatement.setString(1, facultate);
            resultSet = preparedStatement.executeQuery();
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
                List<String> preferinte = new ArrayList<>();
                preferinte.add(preferinta1);
                preferinte.add(preferinta2);
                preferinte.add(preferinta3);
                preferinte.add(preferinta4);
                preferinte.add(preferinta5);
                Student student = new com.example.server.Student(id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinte, connection);
                studenti.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return studenti;
    }

    public void SetAllReinscrisCuNull() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE studenti1 SET reinscris_la_camin = null");
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a actualizat cu succes numarul de locuri fete.");
            } else {
                System.out.println("Nu s-a actualizat niciun rand din tabel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
