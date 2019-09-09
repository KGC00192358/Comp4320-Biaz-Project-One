import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner;
import java.util.Date;

public class myFirstTCPClient {

	public static void main(String[] args) throws IOException {

		if ((args.length < 1) || (args.length > 2))  // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");

		String server = args[0];       // Server name or IP address

		int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;

		// Create socket that is connected to server on specified port
		String outString = "";
		while(true) {
			FileWriter outFileStream = new FileWriter("./TCPRoundTripLog.txt");
			PrintWriter formattedOutStream = new PrintWriter(outFileStream);
			formattedOutStream.print(outString);
			formattedOutStream.close();


			Socket socket = new Socket(server, servPort);
			System.out.println("Please input a message");	
			Scanner msgIn = new Scanner(System.in);
			String msg = msgIn.nextLine();
			// Convert input String to bytes using the default character encoding


			PrintWriter pr = new PrintWriter(socket.getOutputStream());
			long startTime = System.currentTimeMillis(); 
			System.out.println("Connected to server...sending echo string");


			pr.println(msg);  // Send the string to the server
			pr.flush();
			

			InputStreamReader in = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String rcvMsg = br.readLine();
			long endTime = System.currentTimeMillis();
			long roundTripTime = endTime - startTime;

			System.out.println("Received: " + rcvMsg);
			System.out.println("Round Trip Time: " + String.valueOf(roundTripTime) + "ms");
			outString = outString + msg + "\t" + String.valueOf(roundTripTime) + "ms\n";
						socket.close();  // Close the socket and its streams


		}
	}
}
