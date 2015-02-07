package csv;

public class CsvWriteReadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String fileName = System.getProperty("user.home")+"/transaction1.csv";
		
		System.out.println("Write CSV file:");
		WriteTransactionCsv.writeCsvFile(fileName);
		//CsvFileWriter.writeCsvFile(fileName);
		
		
	}

}