package com.simplechat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleChatClient {
    private JTextArea chatArea;
    private JTextField messageField;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }

    public void go() {
        JFrame frame = new JFrame("Simple Chat Client");

        // Create a JPanel to hold the chat area, message field, and send button.
        JPanel mainPanel = new JPanel();

        // Create a JTextArea to display the chat messages.
        chatArea = new JTextArea(15, 50);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);

        // Add the JTextArea to a JScrollPane.
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Create a JTextField for the user to enter their chat messages.
        messageField = new JTextField(20);

        // Create a JButton to send the chat message.
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        // Add the chat area, message field, and send button to the main panel.
        mainPanel.add(chatScrollPane);
        mainPanel.add(messageField);
        mainPanel.add(sendButton);

        // Set up the networking.
        setUpNetworking();

        // Create a new thread to handle incoming messages from the server.
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        // Add the main panel to the frame and show the frame.
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    /**
     * Set up the networking by creating a new Socket and InputStreamReader, and initializing the BufferedReader
     * and PrintWriter.
     */
    @SuppressWarnings("resource")
    public void setUpNetworking() {
        try{
            Socket socket = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Networking established.");
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ActionListener for the send button. Sends the message in the message field to the server via the PrintWriter.
     * Clears the message field and requests focus for the message field.
     */
    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                writer.println(messageField.getText());
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            messageField.setText("");
            messageField.requestFocus();
        }
    }

    /**
     * Runnable for the reader thread. Reads incoming messages from the server via the BufferedReader and appends
     * them to the chat area.
     */
    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("Read from the server: " + message);
                    chatArea.append(message + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
