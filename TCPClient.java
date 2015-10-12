import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {
   private static ArrayList<Long> cycleTimeList = new ArrayList<Long>();
   private static int numLines;

   public static void main(String[] args) throws IOException {
      	
	   // Variables for setting up connection and communication
       Socket Socket = null; // socket to connect with ServerRouter
       PrintWriter out = null; // for writing to ServerRouter
       BufferedReader in = null; // for reading form ServerRouter
       InetAddress addr = InetAddress.getLocalHost();
       String host = addr.getHostAddress(); // Client machine's IP
       String routerName = "127.0.0.1"; // ServerRouter host name
       int SockNum = 5555; // port number
		
       // Tries to connect to the ServerRouter
       try {
           Socket = new Socket(routerName, SockNum);
           out = new PrintWriter(Socket.getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
       } 
       catch (UnknownHostException e) {
           System.err.println("Don't know about router: " + routerName);
           System.exit(1);
       } 
             
        catch (IOException e) {
           System.err.println("Couldn't get I/O for the connection to: " + routerName);
           System.exit(1);
        }
				
      	// Variables for message passing	
        Reader reader = new FileReader("file.txt"); 
		BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
		String address ="127.0.0.1"; // destination IP (Server)
		long t0, t1, t = 0;
		
		// Communication process (initial sends/receives
		out.println(address);// initial send (IP of the destination Server)
		fromServer = in.readLine();//initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromServer);
		out.println(host); // Client sends the IP of its machine as initial send
		t0 = System.currentTimeMillis();
		
      	numLines = 0;
		
		// Communication while loop
        while ((fromServer = in.readLine()) != null) {
           System.out.println("Server: " + fromServer);
           t1 = System.currentTimeMillis();
           if (fromServer.equals("Bye.")) { // exit statement
              break;
           }
           t = t1 - t0;
           System.out.println("Cycle time: " + t);
           numLines++;
           setCycleTimeList(t);

           fromUser = fromFile.readLine(); // reading strings from a file
           if (fromUser != null) {
              System.out.println("Client: " + fromUser);
              out.println(fromUser); // sending the strings to the Server via ServerRouter
              t0 = System.currentTimeMillis();
           }
        }
        
        // writing to CSV file
        String fileName = System.getProperty("user.home")+"/Desktop/stat.csv";
		System.out.println("Write CSV file:");
		CSVFileWriter.writeCsvFile(fileName);
		
		// closing connections
        out.close();
        in.close();
        Socket.close();
        System.out.println("Connections closed.");
   }

   public void setNumLines(int numLines) {
	   TCPClient.numLines = numLines;
   }

   public static int getNumLines() {
	   return numLines;
   }
   
   public static void setCycleTimeList(Long t) {
	   cycleTimeList.add(t);
   }

   public static ArrayList<Long> getCycleTimeList() {
	   return (ArrayList<Long>) cycleTimeList;
   }
}