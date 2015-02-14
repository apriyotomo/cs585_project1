/********************************************
*Mapper
*MapperMapSideJoinDCacheTextFile
********************************************/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapperMapSideJoinDCacheTextFile extends
  	Mapper<LongWritable, Text, Text, Text> {

	private static HashMap<String, String> CustomerMap = new HashMap<String, String>();
	private BufferedReader brReader;
	private String strCountryCode = "";
	private Text txtMapOutputKey = new Text("");
	private Text txtMapOutputValue = new Text("");

	enum MYCOUNTER {
		RECORD_COUNT, FILE_EXISTS, FILE_NOT_FOUND, SOME_OTHER_ERROR
	}

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {

		Path[] cacheFilesLocal = DistributedCache.getLocalCacheFiles(context
				.getConfiguration());

		for (Path eachPath : cacheFilesLocal) {
			if (eachPath.getName().toString().trim().equals("customerinput")) {
				context.getCounter(MYCOUNTER.FILE_EXISTS).increment(1);
				loadCustomersHashMap(eachPath, context);
			}
		}

	}

	private void loadCustomersHashMap(Path filePath, Context context) //getCustomerHashmap
			throws IOException {

		String strLineRead = "";

		try {
			brReader = new BufferedReader(new FileReader(filePath.toString()));

			// Read each line, split and load to HashMap
			while ((strLineRead = brReader.readLine()) != null) {
				String custFieldArray[] = strLineRead.split(",");
				CustomerMap.put(custFieldArray[0].trim(), //custID
						custFieldArray[3].trim());	//countrycode
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			context.getCounter(MYCOUNTER.FILE_NOT_FOUND).increment(1);
		} catch (IOException e) {
			context.getCounter(MYCOUNTER.SOME_OTHER_ERROR).increment(1);
			e.printStackTrace();
		}finally {
			if (brReader != null) {
				brReader.close();

			}

		}
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		context.getCounter(MYCOUNTER.RECORD_COUNT).increment(1);

		if (value.toString().length() > 0) {
			String arrEmpAttributes[] = value.toString().split(",");

			try {
				strCountryCode = CustomerMap.get(arrEmpAttributes[1].toString()); 
			} finally {
				strCountryCode = ((strCountryCode.equals(null) || strCountryCode
						.equals("")) ? "NOT-FOUND" : strCountryCode);
			}

			txtMapOutputKey.set(strCountryCode); //countrycode

			txtMapOutputValue.set(arrEmpAttributes[1].toString() + "," 	//custID
					+ arrEmpAttributes[2].toString());					//trxamount

		}
		context.write(txtMapOutputKey, txtMapOutputValue);
	}
}
