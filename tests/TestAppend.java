import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
 
public class TestAppend{

	public static final Logger logger = Logger.getLogger(TestAppend.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh;
		try
		{ 
			String tosend;

			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/Client.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter); 
	        logger.setUseParentHandlers(false);

			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("localhost"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

			// get interface and output to concole

			// System.out.println(dis.readUTF());
			System.out.println("Starting test...");
			dis.readUTF();

			try {
				File file = new File("Key-Value.txt");
	            //создаем объект FileReader для объекта File
	            FileReader fr = new FileReader(file);
	            //создаем BufferedReader с существующего FileReader для построчного считывания
	            BufferedReader reader = new BufferedReader(fr);
	            // считаем сначала первую строку
	            String line = reader.readLine();
	           // читаем посимвольно
	            while (line != null) { 
	            	dis.readUTF();
					// System.out.println(dis.readUTF());
					tosend = "Append?" + line;
					dos.writeUTF(tosend);
					line = reader.readLine();
					
					// If client sends exit,close this connection 
					// and then break from the while loop 
					if(tosend.equals("Exit")) 
					{ 
						// System.out.println("Closing this connection : " + s); 
						s.close(); 
						// System.out.println("Connection closed"); 
						logger.info("Connection closed");
						break; 
					}
					
					// printing getting response from server
					String received = dis.readUTF(); 
					// System.out.println("Server answer: "+received); 
					logger.info("Server answer: "+received);
				} 
	        } catch (FileNotFoundException e) {
	        	logger.severe("NoSuchFieldException");
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close();
			System.out.println("Test append done!!!"); 

		} catch(Exception e){ 
			logger.severe("Code error!");
			e.printStackTrace(); 
		} 
	} 
} 
