import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class CustomerDataGenerator {
		int CustomerID = 1;
		int totalRecords = 0;
		
		public CustomerDataGenerator(int totalRecords) {
			// TODO Auto-generated constructor stub
			this.totalRecords = totalRecords;
		}
		public void generate(){
			Random numberGenerater = new Random();
			
			try {
		          File file = new File("Customer.txt");
		          BufferedWriter output = new BufferedWriter(new FileWriter(file));
		          while(CustomerID<=totalRecords){
		        	int nameLength = numberGenerater.nextInt(10)+10;
		      		char[] CustomerName = new char[nameLength];
		      		int temp = 0;
		      		while(temp<nameLength){
		      			CustomerName[temp++] = (char) (numberGenerater.nextInt(26)+'a');
		      		}
		      		String CustomerName2 = String.copyValueOf(CustomerName);
		      		System.out.println(String.copyValueOf(CustomerName));
		      		int CustomerAge = numberGenerater.nextInt(61)+10;
		      		int CountryCode = numberGenerater.nextInt(10)+1;
		      		float CustomerSalary = numberGenerater.nextFloat()*9900+100;
		        	output.write(CustomerID+","+CustomerName2+","+CustomerAge+","+CountryCode+","+CustomerSalary+"\n");
		        	CustomerID++;
		          }
		          output.close();
		        } catch ( IOException e ) {
		           e.printStackTrace();
		    }
		}
}
