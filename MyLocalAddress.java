
//this codes is to find the address of my local machine
import java.net.*;


public class MyLocalAddress{
 public static void main(String[] args) {
    try {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);
    }catch(UnknownHostException uhex) {
        System.out.println(
                "could not find local address"
        );
    }
 }   
}
