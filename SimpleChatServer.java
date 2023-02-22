import java.io.*;
import java.util.*;
import java.net.*;

public class SimpleChatServer {
    ArrayList<Object> clientOutputStreams;

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
        clientOutputStreams = new ArrayList<Object>();
        try{
            ServerSocket serverSock = new ServerSocket(5000);
            while(true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("Got a connection");
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    public void tellEveryone (String message) {
        Iterator it = clientOutputStreams.iterator();
        System.out.println(it.hasNext());
        while(it.hasNext()) {
            try{
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
            } catch(Exception ex){ex.printStackTrace();}
        }
    }
}
