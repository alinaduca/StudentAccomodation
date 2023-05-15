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
        System.out.println(accomodation);
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
//                    out.println("Server received the request: " + inputLine);
                    if(inputLine.equals("get-facultati")) {
                        List<String> list;
                        list = accomodation.getFacultati();
                        String mesaj = null;
                        for(String facultate : list) {
                            mesaj = mesaj + ";" + facultate;
                        }
                        System.out.println(mesaj);
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
                            System.out.println(mesaj);
                            out.println(mesaj);
                        }
                        else {
                            if(inputLine.equals("repartizeaza")) {
                                accomodation.RepartizareStudentiInCamin(); //Repartizare2StudentiInCamin dupa reinscrieri
                                out.println("ok");
                            }
                            else {
                                if(inputLine.equals("repartizeaza2")) {
                                    accomodation.Repartizare2StudentiInCamin();
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
                                        System.out.println(mesaj);
                                        out.println(mesaj);
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