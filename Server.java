// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

// Server class 
public class Server 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos);

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace();
			} 
		} 
	} 
} 

// ClientHandler class 
class ClientHandler extends Thread 
{ 
	// declaring variables
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s;
	Hashtable<String, String> data;
	String [] words, keyVal;

	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
		this.data = new Hashtable<String, String>();
	} 

	public String get(String key){
		System.out.println(this.data.get(key)); 
		return this.data.get(key);
	}

	public void set(String key, String value){
		this.data.replace(key, value);
	}
	
	public void append(String key, String value){
		this.data.put(key, value);	
	}

	@Override // переопределение метода из наследованного класса (Thread)
	public void run() 
	{ 
		String received;
		String toreturn;

		try {
			// interface
			dos.writeUTF("Menu:\n\t"+
						"1) Get request: Get?key\n\t"+
						"2) Set request: Set?key&value\n\t"+
						"3) Append request: Append?key&value\n\t"+
						"4) Exit - close connection");
		} catch(IOException e){ 
			e.printStackTrace(); 
		}

		while (true) 
		{ 
			try { 

				// Ask user what he wants (your next request)
				dos.writeUTF("Next request: "); 
				
				// receive the answer from client 
				received = dis.readUTF();  

				if(received.equals("Exit")) 
				{ 
					System.out.println("Client " + this.s + " sends exit..."); 
					System.out.println("Closing connection with : " + s);
					this.s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 

				words = received.split("\\?");
				if (words.length != 2) {
					dos.writeUTF("Invalid input"); 
					continue;
				}
				
				// write on output stream based on the 
				// answer from the client

				switch (words[0]) { 

					case "Get":

						if (data.keySet().contains(words[1])){
							toreturn = get(words[1]);
							dos.writeUTF(toreturn); 
							break;
						} else {
							dos.writeUTF("The "+words[1]+" doesn't exist"); 
							break;
						}

					case "Set":
						keyVal = words[1].split("&");

						if (keyVal.length != 2) {
							dos.writeUTF("Invalid input"); 
							break;
						}

						if (data.keySet().contains(keyVal[0])){
							this.set(keyVal[0], keyVal[1]);
							dos.writeUTF("The "+keyVal[0]+" was changed on "+keyVal[1]); 
							break;
						} else {
							dos.writeUTF("The "+keyVal[0]+" doesn't exist"); 
							break;
						}
						

					case "Append":
						if (!data.keySet().contains(keyVal[0])){
							keyVal = words[1].split("&");
							append(keyVal[0], keyVal[1]);
							dos.writeUTF("The "+keyVal[0]+" with value "+keyVal[1]+" was appended in db "); 
							break;
						} else {
							dos.writeUTF("The "+keyVal[0]+" already exists"); 
							break;
						}
						
					default: 
						dos.writeUTF("Invalid input"); 
						break; 
				} 
			} catch (EOFException e) {
				System.out.println("Client " + this.s + " break channel."); 
				System.out.println("Closing connection with : " + s);
				try{
					this.s.close(); 	
				} catch(IOException e1){ 
					e1.printStackTrace(); 
				}
				
				System.out.println("Connection closed"); 
				break;
			} catch (IOException e) { 
				e.printStackTrace();
			}
		} 
		
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		} catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
