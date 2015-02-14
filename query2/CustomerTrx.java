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
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			String[] temp = new String[tokenizer.countTokens()];
			
			while(tokenizer.hasMoreTokens()){
				temp[i++] = tokenizer.nextToken().toString();
			}

			word.set(temp[1]); //custID
			String outValue = "1,"+temp[2]+""; //string of no of trx and trx amount
			Text outWord = new Text();
			outWord.set(outValue);
			output.collect(word, outWord);
			
		}
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			float sum = 0;
			int count = 0;
			
			while (values.hasNext()) {
				String line = values.next().toString();
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				int i = 0;
				String[] temp = {"0","0"};
				while(tokenizer.hasMoreTokens()){
					temp[i++] = tokenizer.nextToken().toString();
				}
				
				float trans = Float.parseFloat(temp[1]); //trx amount
				int trans_count = Integer.parseInt(temp[0]); //number of trx
				sum += trans;
				count += trans_count;
			}
			String trx = Integer.toString(count) + "," + Float.toString(sum);
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
