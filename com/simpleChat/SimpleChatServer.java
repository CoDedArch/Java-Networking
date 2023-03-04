package com.simpleChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleChatServer {
    // List to keep track of all connected clients
    ArrayList<PrintWriter> clientOutputStreams;

    // Inner class to handle each connected client in a separate thread
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }catch (Exception ex){ex.printStackTrace();}
        }

        public void run() {
            String message;
            try{
                // Read messages from the client and broadcast to all clients
                while((message = reader.readLine())!= null) {
                    System.out.println("read "+ message);
                    tellEveryone(message);
                }
            }catch(Exception ex) {ex.printStackTrace();}
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Server");
        new SimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList<>();
        try{
            // Create a server socket on port 5000 to listen for client connections
            ServerSocket serverSock = new ServerSocket(5000);
            System.out.println("Waiting for client connections...");

            // Loop indefinitely to accept client connections
            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSock.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // Add the client's output stream to the list of output streams
                // to broadcast messages to all clients
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                // Create a new thread to handle the client and start it
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    // Broadcast a message to all connected clients
    public void tellEveryone(String message) {
        Iterator<PrintWriter> it = clientOutputStreams.iterator();
        while(it.hasNext()) {
            try{
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush(); // Ensure that the message is sent immediately
            } catch(Exception ex){ex.printStackTrace();}
        }
    }
}
