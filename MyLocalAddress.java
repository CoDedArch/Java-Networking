
//this code find's the address of my local machine

import java.net.InetAddress;
import java.net.UnknownHostException;


public class MyLocalAddress{
 public static void main(String[] args) {
    try {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);
    }catch(UnknownHostException u_host) {
        System.out.println(
                "could not find local address"
        );
    }
 }   
}
