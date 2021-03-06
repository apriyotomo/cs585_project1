package org.azhar;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class CustomerTrx {
    
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	    String line = value.toString();

	    int i = 0;
	    //String[] temp = line.split(",");
	    StringTokenizer tokenizer = new StringTokenizer(line, ",");
	    String[] temp = new String[tokenizer.countTokens()];
	    
	    while(tokenizer.hasMoreTokens()){
		temp[i++] = tokenizer.nextToken().toString();
	    }

	    word.set(temp[1]);
	    String outValue = temp[2]+"";
	    //float trans = Float.parseFloat(outValue);
	    Text outWord = new Text();
	    outWord.set(outValue);
	    output.collect(word, outWord);
		/*
	    if(Integer.parseInt(temp[3])>=2 && Integer.parseInt(temp[3])<=6){
		word.set(temp[0]);
		String outValue = temp[1]+","+temp[2]+","+temp[3]+","+temp[4];
		Text outWord = new Text();
		outWord.set(outValue);
		output.collect(word, outWord);
	    }*/
	    //word.set(temp[3]);
	    
	    
	}
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	    float sum = 0;
	    int count = 0;
	    while (values.hasNext()) {
		String s = values.next().toString();
		float trans = Float.parseFloat(s);
		sum += trans;
		count++;
	    }
	    String trx = Float.toString(sum) + "," + Integer.toString(count);
	    Text outWord = new Text();
	    outWord.set(trx);
	    output.collect(key, outWord);
	}
    }

    public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(CustomerTrx.class);
	conf.setJobName("CustomerTrx");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(Text.class);

	conf.setMapperClass(Map.class);
	//conf.setCombinerClass(Reduce.class);
	conf.setReducerClass(Reduce.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));

	JobClient.runJob(conf);
    }
}
