package client_tcp;
import java.io.*;
import java.util.*;
import java.net.*;

// A Client that connects via TCP and can communicate with the server 
// by typing in the console. If "END" is typed, closes connection.
public class Client_TCP {
	public static void main(String argv[]) throws Exception
    {
		while (true) {
	        Socket clientSocket = new Socket("localhost", 8001);
	        
	        //Sends message to the server
	        PrintStream ps = new PrintStream(clientSocket.getOutputStream());
	        Scanner scan = new Scanner(System.in);
	        String cMessage ="";
	        InputStreamReader ir = new InputStreamReader(clientSocket.getInputStream());
	        BufferedReader br = new BufferedReader(ir);
		    cMessage = scan.nextLine();
		    ps.println(cMessage);
		    
		    //Reads and displays response from server
		    String message = br.readLine().trim();
		    System.out.println(message);
		    
		    // End connection by typing "END"
	        if (cMessage.trim().equals("END")) {
		        System.out.println("Closing connections");
		        br.close();
		        ir.close();
		        scan.close();
		        ps.close();
		        clientSocket.close();
		        return;
	        }
        }
    }
}
