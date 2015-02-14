import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;

/* This is the only reducer function. */
public class ReducerJoin extends Reducer<Text, Text, Text, Text> {
	private Text word = new Text();
	private Text outWord = new Text();
	private static HashMap<String, Float> CustTrxMap = new HashMap<String, Float>();
	
	@Override
	public void reduce(Text cID, Iterable<Text> values, Context context) throws IOException, InterruptedException 
	{
	    int countCust = 0;
		String[] outString = {"-1","-1","-1","-1"}; // 4 output elements, each for CountryCode, NoOfCust, MinTrx, MaxTrx
		for(Text newval:values)
		{
			String line = newval.toString();
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			
			String[] temp = {"0","0","0","0","0"};
	    	int i = 0;
			
	    	while(tokenizer.hasMoreTokens()){
	    		temp[i++] = tokenizer.nextToken().toString();
	    	}
			
			if(CustTrxMap.containsKey(temp[0])) //add trx amount if cust is in hashmap
			{
				float newAmount = Float.parseFloat(temp[1]) + CustTrxMap.get(temp[0]);
				CustTrxMap.put(temp[0].trim(), //custID
						newAmount); //newtrxAmount
			}
			else //add new cust & the trx amount to the hashmap
			{
				CustTrxMap.put(temp[0].trim(), //custID
						Float.parseFloat(temp[1]));	//trx amount
				countCust++;
			}
		}
		
		Collection c = CustTrxMap.values();
		outString[1] = Integer.toString(countCust); //noOfcust
		outString[2] = Collections.max(c).toString(); //max
		outString[3] = Collections.min(c).toString(); //min

		String s = outString[1]+","+outString[3]+","+outString[2];
	    outWord.set(s);
		CustTrxMap.clear();
	    context.write(cID, outWord);
	}
}