package com.example.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

public class Accomodation {
    Connection connection;

    public Accomodation(Connection connection) {
        this.connection = connection;
    }

    public void ApelareCSVFacultate (String nume_facultate)
    {
        String procedureName = "export_student_data_facultate";
        String callStatement = "{ call " + procedureName + "('" + nume_facultate + "') }";
        try {
            CallableStatement callableStatement = connection.prepareCall(callStatement);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Procedura Oracle a fost apelată cu succes!");
        } catch (Exception e) {
            System.out.println("A apărut o eroare la apelarea procedurii Oracle: " + e.getMessage());
        }
    }

    public void ApelareCSVCamin (String nume_camin)
    {
        String procedureName = "export_student_data_camin";
        String callStatement = "{ call " + procedureName + "('" + nume_camin + "') }";
        try {
            CallableStatement callableStatement = connection.prepareCall(callStatement);
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Procedura Oracle a fost apelată cu succes!");
        } catch (Exception e) {
            System.out.println("A apărut o eroare la apelarea procedurii Oracle: " + e.getMessage());
        }
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

    public int getNrLocuriFeteDeLaOFaculatePentruUnCamin (String numeFacultate, int idCamin){
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
            preparedStatement = connection.prepareStatement("SELECT * FROM studenti1 WHERE facultate = ? AND camin_repartizat IS NULL AND confirmat_camin IS NULL ORDER BY medie DESC");
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
                Camin camin = new Camin(id, nume, capacitatePerCamera, pret);
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

//    public void Repartizare2StudentiInCamin () {
//        //pentru fiecare facultate
//        List<String> facultati = getFacultati();
////        System.out.println("Facultati: ");
////        System.out.println(facultati.toString());
//        for (String facultate : facultati) {
//            //selectam caminele la care facultatea a primit locuri
////            System.out.println("");
////            System.out.println("Camine la " + facultate);
//            List<Camin> camine = getCaminePentruFacultate(facultate);
////            System.out.println(camine.toString());
//            //selectam studentii ordonati descrescator dupa medie
////            System.out.println("");
////            System.out.println("Studenti care au cerut repartizare dupa medie:");
//            List<Student> studenti = getStudentiDupaMedieDeLaFacultateInSesiunea2(facultate);
////            System.out.println(studenti.toString());
//            for (Student student : studenti) {
//                if(NuERepartizat(student.getId())==true) {
////                    System.out.println(student.toString());
//                    for (String preferinta : student.getPreferinte()) {
////                        System.out.println(preferinta);
//                        if (verificaDisponibilitatePreferinta(preferinta, camine, student) == true) {
////                            System.out.println("am actualizat caminul studentului " + student.getFirstName() + " cu valloarea " + preferinta);
//                            updateRepartizareCaminPentruStudent(student.getId(), preferinta);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        SetAllReinscrisCuNull();
//    }

//    public List<Student> getStudentiDupaMedieDeLaFacultateInSesiunea2 (String facultate){
//        List<Student> studenti = new ArrayList<>();
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            preparedStatement = connection.prepareStatement("SELECT * FROM studenti1 WHERE facultate = ? AND reinscris_la_camin = 1 ORDER BY medie DESC");
//            preparedStatement.setString(1, facultate);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String nume = resultSet.getString("nume");
//                String prenume = resultSet.getString("prenume");
//                String gen = resultSet.getString("gen");
//                String nr_matricol = resultSet.getString("nr_matricol");
//                String email = resultSet.getString("email");
//                String telefon = resultSet.getString("telefon");
//                double medie = resultSet.getDouble("medie");
//                String preferinta1 = resultSet.getString("preferinta1");
//                String preferinta2 = resultSet.getString("preferinta2");
//                String preferinta3 = resultSet.getString("preferinta3");
//                String preferinta4 = resultSet.getString("preferinta4");
//                String preferinta5 = resultSet.getString("preferinta5");
//                List<String> preferinte = new ArrayList<>();
//                preferinte.add(preferinta1);
//                preferinte.add(preferinta2);
//                preferinte.add(preferinta3);
//                preferinte.add(preferinta4);
//                preferinte.add(preferinta5);
//                Student student = new com.example.server.Student(id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinte, connection);
//                studenti.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                resultSet.close();
//                preparedStatement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return studenti;
//    }

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
    public List<Camin> getCamine() {
        List<Camin> camine = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM camine");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                int capacitatePerCamera = resultSet.getInt("capacitate_per_camera");
                int pret = resultSet.getInt("pret");
                Camin camin = new Camin(id, nume, capacitatePerCamera, pret);
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

    public void setAllCaminRepartizatNull()
    {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE studenti1 SET camin_repartizat = null");
            int rowsUpdated = preparedStatement.executeUpdate();
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

    public void inserareStudent(String lastName, String firstName, String nrMatricol, String email, String telefon, String facultate, double medie, String gen, String camin1, String camin2, String camin3, String camin4, String camin5) {
        if(camin1.equals("null")) {
            camin1 = null;
        }
        if(camin2.equals("null")) {
            camin2 = null;
        }
        if(camin3.equals("null")) {
            camin3 = null;
        }
        if(camin4.equals("null")) {
            camin4 = null;
        }
        if(camin5.equals("null")) {
            camin5 = null;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT MAX(id)+1 FROM studenti1");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("MAX(id)+1");
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
        preparedStatement = null;
        try {
            String sqlcommand = "INSERT INTO studenti1 (id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinta1, preferinta2, preferinta3, preferinta4, preferinta5) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sqlcommand);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, gen);
            preparedStatement.setString(5, nrMatricol);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, telefon);
            preparedStatement.setString(8, facultate);
            preparedStatement.setDouble(9, medie);
            preparedStatement.setString(10, camin1);
            preparedStatement.setString(11, camin2);
            preparedStatement.setString(12, camin3);
            preparedStatement.setString(13, camin4);
            preparedStatement.setString(14, camin5);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Studentul a fost inserat in baza de date.");
            }
            else {
                System.out.println("Studentul nu a putut fi inserat in baza de date.");
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

    public String verificareRepartitie(String nrMatricol) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int nrLinii = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM studenti1 WHERE nr_matricol = ?");
            preparedStatement.setString(1, nrMatricol);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nrLinii = resultSet.getInt("count(*)");
                if(nrLinii < 1) {
                    return "Studentul nu exista in baza de date.";
                }
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
        preparedStatement = null;
        resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT camin_repartizat FROM studenti1 WHERE nr_matricol = ?");
            preparedStatement.setString(1, nrMatricol);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String rezultatRepartitie = resultSet.getString("camin_repartizat");
                System.out.println("Studentul a fost repartizat la caminul " + rezultatRepartitie + ".");
                return rezultatRepartitie;
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
        return null;
    }

    public void delete(String nrMatricol) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM studenti1 WHERE nr_matricol LIKE ?");
            preparedStatement.setString(1, nrMatricol);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Studentul s-a sters cu succes.");
            }
            else {
                System.out.println("Studentul nu a putut fi sters.");
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

    public void putNullOnCaminRepartizat(String nrMatricol) {
        PreparedStatement preparedStatement = null;
        String caminRepartizat = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE studenti1 SET camin_repartizat = ? WHERE nr_matricol = ?");
            preparedStatement.setString(1, caminRepartizat);
            preparedStatement.setString(2, nrMatricol);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("S-a eliminat cu succes caminul repartizat.");
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

    public void confirmaLoc(String nrMatricol) {
        PreparedStatement preparedStatement = null;
        int confirmare = 1;
        try {
            preparedStatement = connection.prepareStatement("UPDATE studenti1 SET confirmat_camin = ? WHERE nr_matricol = ?");
            preparedStatement.setInt(1, confirmare);
            preparedStatement.setString(2, nrMatricol);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Repartizarea a fost confirmata cu succes.");
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

    public String getDetaliiCamin(String camin) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT capacitate_per_camera, pret, adresa_camin FROM camine WHERE nume LIKE ?");
            preparedStatement.setString(1, camin);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String capacitate = resultSet.getString("capacitate_per_camera");
                String pret = resultSet.getString("pret");
                String adresa = resultSet.getString("adresa_camin");
                return capacitate + ";" + pret + ";" + adresa;
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
        return null;
    }

}
