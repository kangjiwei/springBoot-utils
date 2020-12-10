package com.test.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * k2 v2 k3 v3
 */
public class WordCountReduce extends Reducer<Text, LongWritable, Text, LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        //2 -- 3
        long count = 0;
        for (LongWritable ll : values) {
            count += ll.get();
        }
        context.write(key,new LongWritable(count));
    }
}
