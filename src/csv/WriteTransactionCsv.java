package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author ashraf
 * 
 */
public class WriteTransactionCsv {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "TransID,CustID,TransTotal,TransNumItems,TransDesc";

	public static void writeCsvFile(String fileName) {
		
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new trx object list to the CSV file
			
			
			for(int i = 1 ; i <= 50; i++)
			{
				Random rand = new Random();
				fileWriter.append(String.valueOf(i));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextInt((50000 - 1) + 1) + 1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextFloat() * (1000-10) + 10));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextInt((10 - 1) + 1) + 1));
				fileWriter.append(COMMA_DELIMITER);
				//description
				char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				int k= rand.nextInt((50 - 20) + 1) + 20;
				for (int j = 0; j < k; j++) {
				    char c = chars[random.nextInt(chars.length)];
				    sb.append(c);
				}
				fileWriter.append(String.valueOf(sb));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}
