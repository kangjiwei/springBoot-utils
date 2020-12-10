package com.test.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *  <k1,v1,k2,v2>
 *  keyin,vin,kout,vout
 */
public class WordCountMapper extends Mapper<LongWritable, Text,Text,LongWritable> {


    /**
     * @param key k1
     * @param value v1
     * @param context 上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //如何把k1v1 转成k2v2
        String[] split = value.toString().split(",");
        LongWritable  longWritable = new LongWritable(1);
        Text text = new Text();
        for(String k:split){
            text.set(k);
            context.write(text,longWritable);
        }
    }
}
