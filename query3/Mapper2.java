package org.eric;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

/* This is the mapper function for customer dataset. */
public class Mapper2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	private Text word = new Text();
	private Text outWord = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		String line = value.toString();

		int i = 0;

		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		String[] temp = new String[tokenizer.countTokens()];
	    
		while(tokenizer.hasMoreTokens()){
			temp[i++] = tokenizer.nextToken().toString();
	    }
	    word.set(temp[1]);
	    outWord.set("Transaction"+","+temp[0]+","+temp[2]+","+temp[3]+","+temp[4]);
	   	output.collect(word, outWord);
	}
}
