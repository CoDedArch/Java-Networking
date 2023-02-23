import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GetRemoteTime extends JFrame 
        implements ActionListener {
            private final JTextField hostInput;
            private final JTextArea display;
    private final JButton exitButton;
    private static Socket socket = null;

            public static void main(String[] args) {
                GetRemoteTime frame = new GetRemoteTime();
                frame.setSize(400, 300);
                frame.setVisible(true);

                //frame.addWindowListener(new frameListener());
            }
                
            // public class frameListener implements WindowListener {
            //     public void windowClosing(WindowEvent event) {
            //         if (socket != null) {
            //             try{
            //                 socket.close();
            //             }
            //             catch(IOException ioEx) {
            //                 System.out.println(
            //                     "\nUnable to close link!\n"
            //                 );
            //                 System.exit(1);
            //             }
            //         }
            //         System.exit(0);
            //     }

            // }

            public GetRemoteTime() {
                hostInput = new JTextField(20);
                add(hostInput, BorderLayout.NORTH);

                display = new JTextArea(10, 15);
                //Following two lines ensures that word-wrapping
                //occurs withing the JTextArea..
                display.setLineWrap(true);
                display.setWrapStyleWord(true);

                add(new JScrollPane(display), BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                JButton timeButton = new JButton("Get date and time");
                timeButton.addActionListener(this);
                buttonPanel.add(timeButton);

                exitButton = new JButton("Exit");
                exitButton.addActionListener(this);

                buttonPanel.add(exitButton);

                add(buttonPanel, BorderLayout.SOUTH);
            }

            public void actionPerformed(ActionEvent event) {
                if(event.getSource() == exitButton)
                    System.exit(0);
                
                String theTime;
                //accept host name from the user...
                String host = hostInput.getText();
                final int DAYTIME_PORT = 13;
                try{
                    //create a socket object to connect to the 
                    //specified host on the relevant port...
                    socket = new Socket(host, DAYTIME_PORT
                    );

                    //create an input stream for the socket 
                    Scanner input =
                        new Scanner(socket.getInputStream());

                    //accept the hosts response via the stream
                    theTime = input.nextLine();

                    //add the host's response to the text in 
                    //the jText area
                    display.append("The date/time at " + host + " is " + theTime + "\n");

                    hostInput.setText(" ");
                }
                catch (UnknownHostException uhEx) {
                    display.append("No such host!\n");
                    hostInput.setText("");
                }
                catch(IOException ioEx) {
                    display.append(ioEx + "\n");
                }
                finally {
                    try{
                        if(socket != null) {
                            socket.close();
                        }
                    }
                    catch (IOException ioEx) {
                        System.out.println("Unable to disconnect!");
                        System.exit(1);
                    }
                }
            }
}
 