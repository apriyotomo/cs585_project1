package org.eric;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

/* This is the only reducer function. */
public class Reducer1 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	private Text word = new Text();
	private Text outWord = new Text();

	public void reduce(Text cID, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException 
	{
	    int Number = 0;
	    int MinItem = 100;
		float TotalSum = 0;
		String[] outString = {"-1","-1","-1","-1","-1","-1"}; // 6 output elements, each for CustomerID, Name, Salary, #Transaction, TotalSum, MinItem.
		
		while(values.hasNext()){
			String line = values.next().toString();
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			String[] temp = {"0","0","0","0","0"};
	    	int i = 0;
	    	while(tokenizer.hasMoreTokens()){
	    		temp[i++] = tokenizer.nextToken().toString();
	    	}
	    	
	    	if(temp[0].equals("Customer")){
	    		outString[1] = temp[1];
	    		outString[2] = temp[4];
	    	}
	    	else if(temp[0].equals("Transaction")){
	    		TotalSum += Float.parseFloat(temp[2]);
	    		if(MinItem > Integer.parseInt(temp[3])){
	    			MinItem = Integer.parseInt(temp[3]);
	    		}
	    	}
			Number++;
	    }
	    outString[3] = Integer.toString(Number);
	    outString[4] = Float.toString(TotalSum);
	    outString[5] = Integer.toString(MinItem);
		String s = outString[1]+","+outString[2]+","+outString[3]+","+outString[4]+","+outString[5];
	    outWord.set(s);
	    output.collect(cID, outWord);
	}
}
