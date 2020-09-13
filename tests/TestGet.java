import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.*;
 
public class TestGet {

	public static final Logger logger = Logger.getLogger(TestGet.class.getName());

	public static void main(String[] args) throws IOException 
	{
		FileHandler fh;
		try {

			String tosend, received, line;
			String [] words;
			long endTime, startTime, duration; // variables for time execution
			Integer wrong=0;


			// настройка вывода логов в файл и выключение вывода логов в консоль
			fh = new FileHandler("logs/TestGet.log");
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
					logger.info("Server answer: "+received); // logging answer
					line = reader.readLine(); // go to next line from est file
				}
				reader.close(); // закрываем прочитанный файл
				fr.close();

				// проверяем наличие записей
				fr = new FileReader(file);
				reader = new BufferedReader(fr);
				line = reader.readLine();
				while (line != null) { 

	            	dis.readUTF(); // from server: input next command
	            	words = line.split("&");
					tosend = "Get?" + words[0]; // formed request
					dos.writeUTF(tosend); // send request
					
					received = dis.readUTF(); // put getting response from server to variable
					
					if ( !received.equals(words[1]) ) {
						logger.info("Wrong answer for: "+words[0]+": "+words[1]);
						wrong += 1;
					}

					line = reader.readLine(); // go to next line from est file
				}

				// check note, is not in db
				dis.readUTF(); 
				tosend = "Get?shurik";
				dos.writeUTF(tosend);
				received = dis.readUTF(); 
				if ( !received.equals("The shurik doesn't exist") ) {
					logger.info("Wrong answer for not existing note");
					wrong += 1;
				}

				reader.close(); // закрываем прочитанный файл
				fr.close();

				// check results
				if ( wrong == 0 ) {
					System.out.println("Test get - done!!!"); 
					logger.info("Test get - done!!!");
				} else {
					System.out.println("Test get - failed! Wrong answers: "+wrong); 
					logger.severe("Test get - failed! Wrong answers: "+wrong);
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
