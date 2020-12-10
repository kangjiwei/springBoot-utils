package com.test.shuffle;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JobMain extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        //1,创建job
        Job pm = Job.getInstance(super.getConf(), "Partion_mapreduce");
        //2,创建job任务进行配置（八个步骤）
        //1,设置输入类和输入类型
        pm.setInputFormatClass(TextInputFormat.class);
        //2,设置Mapper类和数据类型
        TextInputFormat.addInputPath(pm, new Path("hdfs://node1:8020/partition"));
        pm.setMapperClass(PartitionMapper.class);
        pm.setMapOutputKeyClass(Text.class);
        pm.setMapOutputValueClass(NullWritable.class);
        //3,设置Partition,指定分区类型
        pm.setPartitionerClass(Mypartitioner.class);
        //4,第四，五，六步
        //5,指定Reducer类和数据类型（k3和v3）
        pm.setReducerClass(PartitionReduce.class);
        pm.setOutputValueClass(Text.class);
        pm.setOutputValueClass(NullWritable.class);
        //3,设置ReduceTask个数
        pm.setNumReduceTasks(2);
        //指定数据类和输出类型
        pm.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(pm, new Path("hdfs://node1:8020/out/partition_out1"));

        //3,等待任务结束
        boolean b = pm.waitForCompletion(true);
        return b ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration entries = new Configuration();
        int run = ToolRunner.run(entries, new JobMain(), args);
        System.exit(run);

    }
}
