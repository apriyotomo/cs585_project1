package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "ID,Name,Age,CountryCode,Salary";

	public static void writeCsvFile(String fileName) {
		
		/*Create new students objects
		Student student1 = new Student(1, "Ahmed", "Mohamed", "M", 25);
		Student student2 = new Student(2, "Sara", "Said", "F", 23);
		Student student3 = new Student(3, "Ali", "Hassan", "M", 24);
		Student student4 = new Student(4, "Sama", "Karim", "F", 20);
		Student student5 = new Student(5, "Khaled", "Mohamed", "M", 22);
		Student student6 = new Student(6, "Ghada", "Sarhan", "F", 21);
		
		//Create a new list of student objects
		List<Student> students = new ArrayList<Student>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		students.add(student4);
		students.add(student5);
		students.add(student6);
		*/
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			/*
			for (Student student : students) {
				fileWriter.append(String.valueOf(student.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getFirstName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getLastName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getGender());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getAge()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			*/
			
			for(int i = 1 ; i <= 50000; i++)
			{
				Random rand = new Random();
				fileWriter.append(String.valueOf(i));
				fileWriter.append(COMMA_DELIMITER);
				char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				int k= rand.nextInt((20 - 10) + 1) + 10;
				for (int j = 0; j < k; j++) {
				    char c = chars[random.nextInt(chars.length)];
				    sb.append(c);
				}
				fileWriter.append(String.valueOf(sb));
				//fileWriter.append("Customer " + i);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextInt((70 - 10) + 1) + 10));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextInt((10 - 1) + 1) + 11));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(rand.nextFloat() * (10000-100) + 100));
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
