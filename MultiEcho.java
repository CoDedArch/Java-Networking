import java.io.*;
import java.net.*;
import java.util.Scanner;

// testing the importance of having to use
// multi threading to handle multiple clients
public class MultiEcho {
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) 
    throws IOException
    {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(IOException ioEx)
        {
            System.out.println(
                "\nUable to set up port!"
            );
            System.exit(1);
        }

        do
        {
            //wait for client to connect
            Socket client = serverSocket.accept();
            System.out.println("\nNew Client accepted.\n");

            //create a thread to handle comm with 
            //this client and pass the constructor for this 
            //thread a refrence to the relevant socket
            ClientHandler handler = 
                                new ClientHandler(client);
            handler.start(); 

        } while(true);

    }
}


class ClientHandler extends Thread
{
    private Socket client;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket)
    {
        client = socket;
        try
        {
            input = new Scanner (client.getInputStream());
            output = new PrintWriter (client.getOutputStream());

        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
    
    public void run()
    {
        String recieved;
        do
        {
            //accept message from client 
            //the socket's input stream..
            recieved = input.nextLine();
            System.out.println(recieved);
            //echo message back to the client on
            //the socket's output stream..
            if (recieved != null)
                output.println("ECHO: " + recieved);
                System.out.println("Finished: printing back");
            //repeat until quit is sent by the user 
        }while(!recieved.equals("QUIT"));

        try 
        {
            if (client != null)
            {
                System.out.println(
                    "Closing down connection..."
                );
                client.close();
            }
        }
        catch (IOException e){
            System.out.println("Unable to disconnect!");
        }
    }

}
