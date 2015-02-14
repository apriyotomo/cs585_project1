/********************************************
*Driver
*DriverMapSideJoinDCacheTxtFile
********************************************/

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.*;


public class DriverMapSideJoinDCacheTxtFile extends Configured implements Tool {

  @Override
	public int run(String[] args) throws Exception {

		if (args.length != 2) {
			System.out
					.printf("Two parameters are required- <input dir> <output dir>\n");
			return -1;
		}

		Job job = new Job(getConf());
		Configuration conf = job.getConfiguration();
		job.setJobName("Map-side join with text lookup file in DCache");
		DistributedCache
				.addCacheFile(
						new URI(
								"/home/ubuntu/Workspace/hadoop-1.1.0/hadoop-data/customerinput"), //Path of customer data source
						conf);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setJarByClass(DriverMapSideJoinDCacheTxtFile.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(MapperMapSideJoinDCacheTextFile.class);
		job.setReducerClass(ReducerJoin.class);

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(),
				new DriverMapSideJoinDCacheTxtFile(), args);
	}
}

