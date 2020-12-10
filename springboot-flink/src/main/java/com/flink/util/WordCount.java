package com.flink.util;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.junit.Test;

/**
 * 批处理word count
 */
public class WordCount {

    @Test
    public void execute() throws Exception {
        //创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //从文件中读取数据
        String path = "C:\\Users\\Admin\\Documents\\springboot-utils\\springboot-flink\\src\\main\\resources\\hello.txt";
        DataSource<String> dataSource = env.readTextFile(path);
        //对数据集进行处理，分词
        DataSet<Tuple2<String, Integer>> dataset = dataSource.flatMap(new MyFlatMap())
                .groupBy(0)  //按照第一个位置的word分组
                .sum(1);//将第二个位置上的数据求和
        dataset.print();
    }


    public static class MyFlatMap implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] vals = s.split(" ");
            System.out.println("========== " + vals.toString());
            for (String word : vals) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }

}
