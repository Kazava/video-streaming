package client_tcp;
import java.io.*;
import java.util.*;
import java.net.*;

// A Client that connects via TCP and can communicate with the server 
// by typing in the console. If "END" is typed, closes connection.
public class Client_TCP {
	public static void main(String argv[]) throws Exception
    {
		PrintStream ps = null;
		Scanner scan = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		DatagramSocket clientSocketUDP = null;

		Socket clientSocketTCP = null;
		
		while (true) {
			debug("Choose connection between TCP and UDP: ");
	        scan = new Scanner(System.in);
	        String choice = "";
	        choice = scan.nextLine();
	        if (choice.equals("TCP"))
	        	clientSocketTCP = new Socket("localhost", 8001);
	        else if (choice.equals("UDP")) {	        	 
	            InetAddress IPAddress = InetAddress.getByName("localhost");
	        	clientSocketUDP = new DatagramSocket();
	        }
	        String clientCommand = "";
	        clientCommand = openConnections(choice, ps, scan, isr, br, clientSocketTCP, clientSocketUDP, InetAddress.getByName("localhost"));
		    
		    // End connection by typing "END"
	        if (clientCommand.equals("END")) {
		        closeConnections(ps, scan, isr, br);
		        clientSocketTCP.close();
		        return;
	        }
        }
    }
	
	public static String openConnections(String socketType, PrintStream ps, Scanner scan, InputStreamReader isr, 
								BufferedReader br, Socket tcp, DatagramSocket udp, InetAddress address) throws IOException {
		debug("opening connections");
		if (socketType.equals("TCP")) {
			ps = new PrintStream(tcp.getOutputStream());
			debug("Write anything");
			Scanner sc = new Scanner(System.in);
			String cMessage = "";
		    cMessage = scan.nextLine();
			isr = new InputStreamReader(tcp.getInputStream());
			br = new BufferedReader(isr);
		    ps.println(cMessage);
		    // Print response:
		    String message = br.readLine().trim();
		    System.out.println(message);
		    return message;
		}
		if (socketType.equals("UDP")) {
			debug("entered UDP, type anything:");
		    byte[] receiveData = new byte[1024];
		    byte[] sendData = new byte[1024];
		    Scanner sc = new Scanner(System.in);
			String cMessage = "";
		    cMessage = scan.nextLine();
		    sendData = cMessage.getBytes();
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 8001);
		    udp.send(sendPacket);
		    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    udp.receive(receivePacket);
		    debug("Received udp-packet");
		    String modifiedSentence = new String(receivePacket.getData());
		    System.out.println("FROM SERVER:" + modifiedSentence);
		    return "upd";
		}
		else 
			return "fail";
	}
	public static void closeConnections(PrintStream ps, Scanner scan, InputStreamReader isr, 
										BufferedReader br) throws IOException {
        System.out.println("Closing connections");
		if (ps != null)
			ps.close();
		if (scan != null)
			scan.close();
		if (isr != null)
			isr.close();
		if (br != null)
			br.close();
	}
	
	  // Because "System.out.println" is too long to type...
	public static void debug(String str) {
	  	System.out.println(str);
	}
}
