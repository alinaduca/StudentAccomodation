package com.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Main server;

    public ClientThread(Socket clientSocket, Main server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try  {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
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
                    out.println("Server received the request: " + inputLine);
                }
            }
        } catch(IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}