// package Client;
// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
// import java.util.logging.Level;
// import java.util.logging.Logger;
// import java.util.logging.FileHandler;

// Client class 
public class ClientForNetwork
{ 
	public static final Logger logger = Logger.getLogger(ClientForNetwork.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh; // file for logs
		try
		{ 

			long endTime, startTime, duration; // variables for time execution

			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/ClientForNetwork.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter); 
	        logger.setUseParentHandlers(false);

			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("0.0.0.0"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

			// get interface and output to concole

			System.out.println(dis.readUTF()); 
			logger.info("request to server");
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			while (true) 
			{ 
				System.out.println(dis.readUTF()); 
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend); 
				
				startTime = System.nanoTime(); // засекаем время

				// If client sends exit,close this connection and then break from the while loop
				if(tosend.equals("Exit")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					logger.info("Connection closed");
					break; 
				} 
				
				// printing getting response from server
				String received = dis.readUTF(); 
				System.out.println("Server answer: "+received); 
				endTime = System.nanoTime();
				duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				logger.info("Server answer: "+received+"\n\tRequest execution time: "+duration+" ms");
			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		} catch (ConnectException e) {
			logger.warning("Conection refused. Server is down");
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
} 
