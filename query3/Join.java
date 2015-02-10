package org.eric;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.*;
import org.apache.hadoop.util.*;

public class Join {
    
    /*public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	//private final static IntWritable one = new IntWritable(1);
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
	    if(Integer.parseInt(temp[3])>=2 && Integer.parseInt(temp[3])<=6){
			word.set(temp[0]);
			String outValue = temp[1]+","+temp[2]+","+temp[3]+","+temp[4];
			Text outWord = new Text();
			outWord.set(outValue);
			output.collect(word, outWord);
	    }
	}
    }*/

    /*public static class Reduce extends MapReduceBase implements Reducer<Text, Iterator<Text>, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	    int sum = 0;
	    while (values.hasNext()) {
		sum += 1;
	    }
	    Text total = new Text(sum+"");
	    output.collect(key, total);
	}
    }*/

    public static void main(String[] args) throws Exception {
	JobConf conf = new JobConf(Join.class);
	conf.setJobName("Join");

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(Text.class);

	//conf.setMapperClass(Mapper1.class);
	
	//conf.setCombinerClass(Reducer1.class);
	
	
	conf.setReducerClass(Reducer1.class);

	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);

	MultipleInputs.addInputPath(conf, new Path(args[0]), TextInputFormat.class, Mapper1.class);
	MultipleInputs.addInputPath(conf, new Path(args[1]), TextInputFormat.class, Mapper2.class);
	//FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[2]));

	JobClient.runJob(conf);
    }
}
