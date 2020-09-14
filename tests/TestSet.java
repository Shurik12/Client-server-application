import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
 
public class TestSet {

	public static final Logger logger = Logger.getLogger(TestSet.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh;
		try {

			String tosend, received, line;
			String [] examples = {"Append?shurik&1234", "Append?mike&dwqfdwq", "Append?frewt&vfd"};
			long endTime, startTime, duration; // variables for time execution
			Integer wrong=0;


			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/TestSet.log");
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
			System.out.println("Starting set test...");
			dis.readUTF();

			try {

	            startTime = System.nanoTime(); // засекаем время

	           // читаем построчно и добавляем записи в базу
	            for (Integer i=0; i<examples.length; i++) { 

	            	dis.readUTF(); // from server: input next command
					tosend = examples[i]; // formed request
					dos.writeUTF(tosend); // send request
					
					received = dis.readUTF(); // put getting response from server to variable
					logger.info("Input: "+tosend+"\tOutput: "+received);
				}

				// изменяем и проверяем записи

            	dis.readUTF(); // from server: input next command
				tosend = "Set?shurik&4321"; // formed request
				dos.writeUTF(tosend); // send request
				received = dis.readUTF(); // put getting response from server to variable
				
				dis.readUTF(); // from server: input next command
				tosend = "Get?shurik"; // formed request
				dos.writeUTF(tosend); // send request
				received = dis.readUTF(); // put getting response from server to variable
				if ( !received.equals("4321") ) {
					logger.info("Wrong answer: 4321 != "+received);
					wrong += 1;
				}

				// check note, is not in db
				dis.readUTF(); 
				tosend = "Set?shurigt43ggk&32465";
				dos.writeUTF(tosend);
				received = dis.readUTF(); 
				if ( !received.equals("The shurigt43ggk doesn't exist") ) {
					logger.info("Wrong answer for not existing note");
					wrong += 1;
				}

				// check results
				if ( wrong == 0 ) {
					System.out.println("Test set - done!!!"); 
					logger.info("Test set - done!!!");
				} else {
					System.out.println("Test set - failed! Wrong answers: "+wrong); 
					logger.severe("Test set - failed! Wrong answers: "+wrong);
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
