import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
 
public class TestLpop {

	public static final Logger logger = Logger.getLogger(TestLpop.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh;
		try {

			String tosend, received, line;
			String [] words;
			long endTime, startTime, duration; // variables for time execution
			Integer i=0;


			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/TestLpop.log");
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
			System.out.println("Starting get test...");
			dis.readUTF();

			try {

				File file = new File("Key-Value.txt");

	            //создаем объект FileReader для объекта File
	            FileReader fr = new FileReader(file);
	            //создаем BufferedReader с существующего FileReader для построчного считывания
	            BufferedReader reader = new BufferedReader(fr);

	            // считаем сначала первую строку
	            line = reader.readLine();
	            startTime = System.nanoTime(); // засекаем время

	           // читаем построчно и добавляем записи в базу
	            while (line != null) { 

	            	dis.readUTF(); // from server: input next command
					tosend = "Append?" + line; // formed request
					dos.writeUTF(tosend); // send request
					
					received = dis.readUTF(); // put getting response from server to variable
					logger.info("Input: "+tosend+"\tOutput: "+received);
					line = reader.readLine(); // go to next line from est file
				}
				reader.close(); // закрываем прочитанный файл
				fr.close();

				// delete notes from db per one
				for (i=0; i<124; i++) { 

	            	dis.readUTF(); // from server: input next command
					tosend = "Lpop";
					dos.writeUTF(tosend); // send request
					
					received = dis.readUTF(); // put getting response from server to variable
					logger.info("Input: "+tosend+"\tOutput: "+received);
				}

				// check results
				dis.readUTF(); // from server: input next command
				tosend = "Lpop";
				dos.writeUTF(tosend); // send request
				
				received = dis.readUTF(); // put getting response from server to variable
				logger.info("Input: "+tosend+"\tOutput: "+received);
				if ( received.equals("The nothing was deleted") ) {
					System.out.println("Test lpop - done!!!"); 
					logger.info("Test lpop - done!!!");
				} else {
					System.out.println("Test lpop - failed!"); 
					logger.severe("Test lpop - failed!");
				}

				// close test
				dis.readUTF(); // from server: input next command
				tosend = "Exit";
				dos.writeUTF(tosend);
				s.close(); 
				logger.info("Connection closed");

				// test timing
				endTime = System.nanoTime();
				duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
				logger.info("Test execution time: "+duration+" ms");

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
