package com.test.shuffle;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * k1 偏移量 LongWritable
 * v1 行文本数据 Text
 *
 * k2 行文本 Text
 * v2 NullWritable
 */
public class PartitionMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    public  enum  partitionType{
        ONE,TWO,THREE;
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //定义计数器
/*      Counter counter = context.getCounter("MY_COUNTER", "partition_counter");
        counter.increment(1);*/
        context.getCounter(partitionType.ONE).increment(1);
        context.write(value,NullWritable.get());
    }

}
