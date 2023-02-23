//advancing in my net lib

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;



public class IPFinder {
        public static void main(String[] args) {
            String host;
            Scanner input = new Scanner(System.in);
            System.out.println("\n\n Enter host name: ");
            host = input.next();
            try (input) {
                InetAddress address = InetAddress.getByName(host);
                System.out.println("IP address:"
                        + address.toString());
            } catch (UnknownHostException uhEx) {
                System.out.println("Could not find " + host);
            }
        }


}
