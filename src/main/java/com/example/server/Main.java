package com.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private int port;
    private ServerSocket serverSocket;
    public Main(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Game server started on port " + port);
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Accomodation accomodation = new Accomodation(connection);
            accomodation.RepartizareStudentiInCamin();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientThread clientThread = new ClientThread(clientSocket, this, accomodation);
                clientThread.start();
            }
        } catch(IOException e) {
            System.err.println("Error starting server on port " + port + ": " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch(IOException e) {
            System.err.println("Error stopping server on port " + port + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        Main gameServer = new Main(12345);
        gameServer.start();
    }
}

