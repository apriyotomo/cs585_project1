import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TransactionDataGenerator {
	int TransID = 1;
	int CustNum = 0;
	int totalRecords = 0;
	
	public TransactionDataGenerator(int totalRecords, int CustNum){
		this.totalRecords = totalRecords;
		this.CustNum = CustNum;
	}
	
	public void generate(){
		Random numberGenerater = new Random();
		
		try {
	          File file = new File("Transaction.txt");
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          while(TransID<=totalRecords){
	        	  int CustID = numberGenerater.nextInt(CustNum)+1;
	        	  float TransTotal = numberGenerater.nextFloat()*990+10;
	        	  int TransNumItems = numberGenerater.nextInt(10)+1;
	        	  int TransDescLength = numberGenerater.nextInt(31)+20;
	        	  char[] TransDesc = new char[TransDescLength];
	        	  int temp = 0;
	        	  while(temp<TransDescLength){
	        		  TransDesc[temp++] = (char) (numberGenerater.nextInt(26)+'a');
	        	  }
	        	  String TransDesc2 = String.copyValueOf(TransDesc);
	        	  output.write(TransID+","+CustID+","+TransTotal+","+TransNumItems+","+TransDesc2+"\n");
	        	  TransID++;
	          }
	          output.close();
	        } catch ( IOException e ) {
	           e.printStackTrace();
	    }
	}
}
