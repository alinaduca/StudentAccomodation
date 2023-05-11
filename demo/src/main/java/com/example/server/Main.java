package com.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clientThread.start();
            }
        } catch(IOException e) {
            System.err.println("Error starting server on port " + port + ": " + e.getMessage());
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch(IOException e) {
            System.err.println("Error stopping server on port " + port + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Main gameServer = new Main(12345);
        gameServer.start();
    }
}

