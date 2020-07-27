import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main program of this serving customers system.
 * Customers who arrive exactly at 5:00 will not be served. 
 * Customers who wait until exactly 5:00 will not be served.
 * @author Xintong Zhu
 * @version 0.1
 */
public class Serving_Customers {
		
	/**
	 * Main method to run the program.
	 * @param args Any argument provided while running the program.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		
		// set the argument for two files names
		String file1 = args[0];
		String file2 = args[1];
	
		// set a variable to store the processing time constants
		int processing = 0;
		
		// open customersfile.txt
//		System.out.println(directory.getAbsolutePath());

			Scanner customersfile;
			try {
				customersfile = new Scanner(new File(file1));
				// initialize a queue to store all customers
				Queue custQue = new Queue();
				// set a variable to store every line containing the id info
				String idLine = "";
				
				// set a variable to find the processing time constant
				int lineNum = 0;
				
				// read the file
				while(customersfile.hasNextLine()) {
					// store the processing time constant at the first line
					if(lineNum == 0) {
						processing = Integer.parseInt(customersfile.nextLine());					
//							System.out.println(customersfile.nextLine());
						// update the line number
						lineNum++;
					} // if lineNum = 0
					else {
						idLine = customersfile.nextLine();
						
						// if the line is not empty, then it is valid and should be further analyzed
						if(!idLine.equals("")) {
//							System.out.println(customersfile.nextLine());
//							System.out.println(idLine);
							// initialize variables to store this customer's id and arrival time
							int currentId = 0;
							int currentArrivalTime = 0;
							int[] timeList = new int[3];
							// analyze and store the id
							String idString = idLine.substring(12, idLine.length());
//							System.out.println(idString);
							currentId = Integer.parseInt(idString);
//							System.out.println(currentId);
							// analyze and store the arrival time by seconds passed after 0:00:00
							// default: a.m.
							String arrivalLine = customersfile.nextLine();
							String rawTime = arrivalLine.substring(14, arrivalLine.length());
							String[] timeListStr = rawTime.split(":"); // split the raw time data by ':'
							int idx = 0;
							// convert the string time to int time
							for (String timeStr: timeListStr) {
								int time = Integer.parseInt(timeStr);
								timeList[idx] = time;
								idx++;
							} // timeListStr
							// calculate the arrival time relative to 0:00:00 by seconds	
							currentArrivalTime = timeList[0] * 3600 + timeList[1] * 60 + timeList[2];																					
							
							// create a customer object for the current one
							Customer currentCustomer = new Customer(currentArrivalTime, currentId);
							// add this customer to the queue
							custQue.enqueue(currentCustomer);
							// check if the arrival time is right, since it may be a.m. or p.m.
							if(custQue.getSize() > 1) {
								// if the last customer's (we have just added) arrival time is samller than the first one, meaning the last customer's arrival time should be p.m.
								if (custQue.last.data.getArrivalTime() < custQue.first.data.getArrivalTime()) {
									// recalculate the time in p.m.
									custQue.last.data.setArrivalTime((timeList[0] + 12) * 3600 + timeList[1] * 60 + timeList[2]);
								} // if last < first
							} // if getSize
							
						} // if != ""
					} // if lineNum != 0				
					
				}// while hasNextLine
				
				// open queriesfile.txt
				try {
					Scanner queriesfile = new Scanner(new File(file2));
					int answer;
					// create a new text file for output
					BufferedWriter outputFile = new BufferedWriter(new FileWriter("output.txt"));
					// use a while loop to read the file
					while (queriesfile.hasNextLine()) {
						String question = queriesfile.nextLine();
						// skip the blank line if there is
						if (! question.equals("")) {
							// see which question is asking
							if (question.equals("NUMBER-OF-CUSTOMERS-SERVED")) {
								answer = custQue.servedCust(processing);
							} // served customers
							else if (question.equals("LONGEST-BREAK-LENGTH")) {
								answer = custQue.longestBreak(processing);
							} // longest break
							else if (question.equals("TOTAL-IDLE-TIME")) {
								answer = custQue.totalIdle(processing);
							} // total idle
							else if (question.equals("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME")) {
								answer = custQue.maxNum(processing);
							} // max number in line
							else {
								// get the checked customer's id
								int checkedId = Integer.parseInt(question.substring(16, question.length()));
//								System.out.println(checkedId);
								answer = custQue.waitTime(checkedId, processing);
							} // wait time
							// write to the file using BufferedWriter
							outputFile.append(question + ":" + answer + "\n");
							System.out.println(question + ":" + answer);
						} // if not blank line
					} // while hasNextLine
					System.out.println();;
					System.out.println("A 'output.txt' file has also been created in this folder.\n");
//					outputFile.write("wulala");
					outputFile.close();
										
				} catch (IOException e) {
					System.out.println("Sorry, 'queriesfile.txt' file not found. Please double check whether you have that file in the same folder as code files.");
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("Sorry, file not found. Please make sure the text file is in the same folder as this code.");
			}
			

			// temporary tester			
//			System.out.println(custQue.first.data.getArrivalTime());
//			System.out.println(custQue.last.data.getArrivalTime());
//			System.out.println(custQue.getSize());
//			System.out.println(custQue.waitTime(1, processing));
//			System.out.println(custQue.waitTime(2, processing));
//			System.out.println(custQue.waitTime(3, processing));
//			System.out.println(custQue.waitTime(4, processing));
//			System.out.println(custQue.waitTime(5, processing));
//			System.out.println(custQue.waitTime(6, processing));
//			System.out.println(custQue.waitTime(7, processing));
//			System.out.println(custQue.waitTime(8, processing));
//			System.out.println(custQue.waitTime(9, processing));
//			System.out.println(custQue.waitTime(10, processing));
//			System.out.println(custQue.waitTime(11, processing));
//			System.out.println(custQue.waitTime(12, processing));
//			System.out.println(custQue.waitTime(13, processing));
//			System.out.println(custQue.waitTime(14, processing));
//			System.out.println(custQue.servedCust(processing));
//			System.out.println(custQue.maxNum(processing));

//			Scanner queriesfile = new Scanner(new File("queriesfile.txt"));
//			System.out.println("Sucess");
			
	} // main
	
}
