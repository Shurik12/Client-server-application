import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
 
public class TestBadCommands{

	public static final Logger logger = Logger.getLogger(TestBadCommands.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh;
		try {

			String tosend;
			long endTime, startTime, duration; // variables for time execution

			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/TestBadCommands.log");
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
			System.out.println("Starting append test...");
			dis.readUTF();

			try {
				File file = new File("bad_commands.txt");
	            //создаем объект FileReader для объекта File
	            FileReader fr = new FileReader(file);
	            //создаем BufferedReader с существующего FileReader для построчного считывания
	            BufferedReader reader = new BufferedReader(fr);
	            // считаем сначала первую строку
	            String line = reader.readLine();
	            String received;

	            startTime = System.nanoTime(); // засекаем время

	           // читаем построчно
	            while (line != null) { 

	            	dis.readUTF(); // from server: input next command
					tosend = line; // formed request
					dos.writeUTF(tosend); // send request
					
					received = dis.readUTF(); // put getting response from server to variable
					logger.info("Input: "+tosend+"\tOutput: "+received);
					line = reader.readLine(); // go to next line from est file
				} 

				reader.close(); // закрываем прочитанный файл
				fr.close();

				// close test
				dis.readUTF(); // from server: input next command
				tosend = "Exit";
				dos.writeUTF(tosend);
				s.close(); 
				logger.info("Connection closed");

				// test timing
				endTime = System.nanoTime();
				duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				System.out.println("Test is ended. \tTest execution time: "+duration+" ms"); 
				logger.info("Test is ended. \tTest execution time: "+duration+" ms");

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

		} catch (ConnectException e) {
			logger.warning("Conection refused. Server is down");
		} catch(Exception e){ 
			logger.severe("Code error!");
			e.printStackTrace(); 
		} 
	} 
} 
