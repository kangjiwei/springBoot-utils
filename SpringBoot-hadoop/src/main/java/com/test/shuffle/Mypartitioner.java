package com.test.shuffle;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


public class Mypartitioner extends Partitioner<Text, NullWritable> {

    /**
     * 制定分区规则
     * @param text
     * @param nullWritable
     * @param i
     * @return
     */
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {
        //拆分文本数据 获取数据
        String[] split = text.toString().split(",");
        System.out.println("刘飞 " + split.toString());
        System.out.println("长度  " + split.length);
        String fenshu = split[1];
        if(Integer.parseInt(fenshu) > 30){
            return 1;
        }
        return 0;
    }
}
