
public class DataGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CustomerDataGenerator CDG = new CustomerDataGenerator(50000);
		//CDG.generate();
		TransactionDataGenerator TDG = new TransactionDataGenerator(5000000,50000);
		TDG.generate();
	}

}
