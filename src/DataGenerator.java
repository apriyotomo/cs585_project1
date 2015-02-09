
public class DataGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* 
		 * This file is for WPI CS585 Project 1.
		 * Run the file to generate random customer and transaction data.
		 * The customer data will be less than 2MB;
		 * the transaction data will be around 300MB, be aware!
		 * */
		CustomerDataGenerator CDG = new CustomerDataGenerator(50000);
		CDG.generate();
		TransactionDataGenerator TDG = new TransactionDataGenerator(5000000,50000);
		TDG.generate();
	}

}
