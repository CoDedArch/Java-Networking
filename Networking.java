import java.io.*;
import java.net.*;

public class Networking {
    String[] adviceList = {"Take smaller bites", "Go for the tight jeans. NO they do Not make ",
                        "One word: inappropriate", "Just for today, be honest. Tell your boss what you *really* think",
                        "You might want to rethink that haircut."};
    public static void main(String[] args){
        //make a socket connection to the server 
        Networking client = new Networking();
        client.go();
    }
    
    public void go() {
        
        try{
            //create a server socket connection 
            ServerSocket serverSock = new ServerSocket(4242);
            while(true){
                Socket sock = serverSock.accept();
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }
        }catch(Exception ex ){
            ex.printStackTrace();
        }
    }
    public String getAdvice(){
        int random = (int)(Math.random() *adviceList.length);
        return adviceList[random];
    }
}
