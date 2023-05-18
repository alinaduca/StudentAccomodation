package com.example.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private ObjectOutputStream objectOut;
    private Accomodation accomodation;
    private PrintWriter out;
    private Main server;

    public ClientThread(Socket clientSocket, Main server, Accomodation accomodation) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.accomodation = accomodation;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error creating input/output streams for client socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                System.out.println("Received command from client: " + inputLine);
                if (inputLine.equals("stop")) {
                    server.stop();
                    out.println("Server stopped");
                    break;
                }
                else {
                    if(inputLine.equals("get-facultati")) {
                        List<String> list;
                        list = accomodation.getFacultati();
                        String mesaj = null;
                        for(String facultate : list) {
                            mesaj = mesaj + ";" + facultate;
                        }
                        out.println(mesaj);
                    }
                    else {
                        System.out.println(inputLine);
                        if(inputLine.equals("get-camine")) {
                            List<Camin> list2 = accomodation.getCamine();
                            String mesaj = null;
                            for(Camin camin : list2) {
                                mesaj = mesaj + ";" + camin.getNume();
                            }
                            out.println(mesaj);
                        }
                        else {
                            if(inputLine.equals("repartizeaza")) {
                                accomodation.RepartizareStudentiInCamin(); //Repartizare2StudentiInCamin dupa reinscrieri
                                out.println("ok");
                                System.out.println("s-a realizat repartizarea");
                            }
                            else {
                                if(inputLine.equals("repartizeaza2")) {
                                    accomodation.RepartizareStudentiInCamin();
                                    out.println("ok");
                                }
                                else {
                                    String[] parts = inputLine.split(":");
                                    if(parts[0].equals("get-camine-pentru-facultate")) {
                                        List<Camin> list = accomodation.getCaminePentruFacultate(parts[1]);
                                        String mesaj = null;
                                        for(Camin camin : list) {
                                            mesaj = mesaj + ";" + camin.getNume();
                                        }
                                        out.println(mesaj);
                                    }
                                    else if(parts[0].equals("insert-student")) {
                                        String lastName = parts[1];
                                        String firstName = parts[2];
                                        String nrMatricol = parts[3];
                                        String email = parts[4];
                                        String telefon = parts[5];
                                        String facultate = parts[6];
                                        String med = parts[7];
                                        double medie = Double.parseDouble(med);
                                        String camin1 = parts[8];
                                        String camin2 = parts[9];
                                        String camin3 = parts[10];
                                        String camin4 = parts[11];
                                        String camin5 = parts[12];
                                        String gen = parts[13];
                                        accomodation.inserareStudent(lastName, firstName, nrMatricol, email, telefon, facultate, medie, gen, camin1, camin2, camin3, camin4, camin5);
                                    }
                                    else if(parts[0].equals("verifica-nr-matricol")) {
                                        out.println(accomodation.verificareRepartitie(parts[1]));
                                    }
                                    else if(parts[0].equals("renunta")) {
                                        accomodation.delete(parts[1]);
                                    }
                                    else if(parts[0].equals("save-list")) {
                                        if(parts[1].contains("Facultatea")) {
                                            out.println(accomodation.ApelareCSVFacultate(parts[1]));
                                        }
                                        else {
                                            out.println(accomodation.ApelareCSVCamin(parts[1]));
                                        }
                                    }
                                    else if(parts[0].equals("turul2")) {
                                        accomodation.putNullOnCaminRepartizat(parts[1]);
                                    }
                                    else if(parts[0].equals("confirma")) {
                                        accomodation.confirmaLoc(parts[1]);
                                    }
                                    else if(parts[0].equals("get-detalii-camin")) {
                                        out.println(accomodation.getDetaliiCamin(parts[1]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}