package com.flink.util;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.junit.Test;

import java.util.Properties;

/**
 * 流处理环境
 *
 * bn-3-p: 提示找不到FlinkKafkaConsumer
 * bn-3-a: 老蔡说，代码要有洁癖。是的！导入无关的东西太多了，导致很多代码相互影响。
 *
 *
 *
 *
 */
public class StreamWordCount {

    public static void main(String[] args) throws Exception {
        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.0.104:9092");
        // only required for Kafka 0.8
        properties.setProperty("zookeeper.connect", "192.168.0.104:2181");
        properties.setProperty("group.id", "test");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        FlinkKafkaConsumer topic = new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties);
        topic.setStartFromEarliest();
        topic.setStartFromLatest();
        topic.setStartFromGroupOffsets();
        DataStream<String> dataSource = env.addSource(topic);
        //对数据集进行处理，分词
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = dataSource.flatMap(new WordCount.MyFlatMap())
                .keyBy(0)
                .sum(1);
        sum.print();
        env.execute();

    }

    @Test
    public void execute() throws Exception {
        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.0.104:9092");
        // only required for Kafka 0.8
        properties.setProperty("zookeeper.connect", "192.168.0.104:2181");
        properties.setProperty("group.id", "test");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        FlinkKafkaConsumer topic = new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties);
        topic.setStartFromEarliest();
        topic.setStartFromLatest();
        topic.setStartFromGroupOffsets();
        DataStream<String> dataSource = env.addSource(topic);
        //对数据集进行处理，分词
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = dataSource.flatMap(new WordCount.MyFlatMap())
                .keyBy(0)
                .sum(1);
        sum.print();
        env.execute();
    }
}
