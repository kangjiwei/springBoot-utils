package com.flink.source;

import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


/**
 * @Author kangjiwei
 * @Date 2020/12/10
 */
public class SourceTest_collections {


    /**
     * 读取集合
     *
     * @throws Exception
     */
    @Test
    public void execute_collections() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<SensorReading> sensorReadings = Arrays.asList(
                new SensorReading("1", 123123123L, 123.2),
                new SensorReading("2", 123123124L, 12.2),
                new SensorReading("3", 123123125L, 23.2),
                new SensorReading("4", 123123126L, 134.23)
        );
        DataStreamSource<SensorReading> data = env.fromCollection(sensorReadings);
        data.print("data");
        DataStreamSource<Integer> in = env.fromElements(1, 2, 3, 4, 5, 6);
        in.print("in");
        env.execute();
    }

    /**
     * 读取文件
     */
    @Test
    public void execute_file() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.readTextFile("D:\\InitSoft\\Project\\springboot-utils\\springboot-flink\\src\\main\\resources\\hello.txt");
        dataStreamSource.print();
        env.execute();
    }


    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStreamSource<String> dataStreamSource = env.readTextFile("C:\\Users\\Admin\\Documents\\springBoot-utils\\springboot-flink\\src\\main\\resources\\sensorReading.txt");
    SingleOutputStreamOperator<SensorReading> map = dataStreamSource.map(new MapFunction<String, SensorReading>() {
        @Override
        public SensorReading map(String s) throws Exception {
            String[] split = s.split(",");
            return new SensorReading(split[0], new Long(split[1]), new Double(split[2]));
        }
    });

    /**
     * 滚动计算,
     *
     * @throws Exception
     */
    @Test
    public void execute_map() throws Exception {
        SingleOutputStreamOperator<SensorReading> filter = map.filter(new FilterFunction<SensorReading>() {
            @Override
            public boolean filter(SensorReading sensorReading) throws Exception {
                return sensorReading.getTemperature() > 200;
            }
        });
        filter.print("filter");

        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = map.flatMap(new FlatMapFunction<SensorReading, String>() {
            @Override
            public void flatMap(SensorReading sensorReading, Collector<String> collector) throws Exception {
                if (sensorReading.getTemperature() > 200) {
                    collector.collect(sensorReading.getId());
                }
            }
        });
        stringSingleOutputStreamOperator.print("flatMap");
        env.execute();
    }

    @Test
    public void execute_juhe() throws Exception {
        KeyedStream<SensorReading, String> keyedStream = map.keyBy(SensorReading::getId);
        SingleOutputStreamOperator<SensorReading> id = keyedStream.min("id");
        id.print("id -- min");
        SingleOutputStreamOperator<SensorReading> id1 = keyedStream.minBy("id");
        id1.print("id1 -- minBy");
        SingleOutputStreamOperator<SensorReading> temperature = keyedStream.max("temperature");
        temperature.print("temperature--max");
        SingleOutputStreamOperator<SensorReading> temperature1 = keyedStream.maxBy("temperature");
        temperature1.print("temperature1--maxBy");

        env.execute();
    }

    @Test
    public void execute_reduce() throws Exception {
        KeyedStream<SensorReading, Tuple> id = map.keyBy("id");
        SingleOutputStreamOperator<SensorReading> reduce = id.reduce(new ReduceFunction<SensorReading>() {
            @Override
            public SensorReading reduce(SensorReading sensorReading, SensorReading t1) throws Exception {
                return new SensorReading(sensorReading.getId(), t1.getTimestamp(), Math.max(sensorReading.getTemperature(), t1.getTemperature()));
            }
        });
        reduce.print("reduce");
        env.execute();
    }

    @Test
    public void execute_split() throws Exception {
        KeyedStream<SensorReading, String> keyedStream = map.keyBy(SensorReading::getId);
        SplitStream<SensorReading> split = keyedStream.split(new OutputSelector<SensorReading>() {
            @Override
            public Iterable<String> select(SensorReading sensorReading) {
                return sensorReading.getTemperature() > 200 ? Collections.singleton("height") : Collections.singleton("low");
            }
        });
        DataStream<SensorReading> height = split.select("height");
        DataStream<SensorReading> low = split.select("low");
        DataStream<SensorReading> select = split.select();
        height.print("height");
        low.print("low");
        select.print("all");
        System.out.println("============================");

        SingleOutputStreamOperator<Tuple2<String, Double>> map = height.map(new MapFunction<SensorReading, Tuple2<String, Double>>() {
            @Override
            public Tuple2<String, Double> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), sensorReading.getTemperature());
            }
        });

        ConnectedStreams<SensorReading, Tuple2<String, Double>> connect = low.connect(map);
        SingleOutputStreamOperator<Object> objectSingleOutputStreamOperator = connect.flatMap(new CoFlatMapFunction<SensorReading, Tuple2<String, Double>, Object>() {
            @Override
            public void flatMap1(SensorReading sensorReading, Collector<Object> collector) throws Exception {
                collector.collect(new Tuple3<String, Double, String>(sensorReading.getId(), sensorReading.getTemperature(), "low"));
            }

            @Override
            public void flatMap2(Tuple2<String, Double> stringDoubleTuple2, Collector<Object> collector) throws Exception {
                collector.collect(new Tuple3<String, Double, String>(stringDoubleTuple2.f0, stringDoubleTuple2.f1, "height"));
            }
        });
        objectSingleOutputStreamOperator.print("connect");
        select.union(height, low);

        env.execute();
    }


    /**
     * <p>
     * 接受kafka数据， addSource
     * 发送kafka数据。 addLink
     * </p>
     *
     * @throws Exception
     */
    @Test
    public void execute_sink() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.20.32:9092");
        // only required for Kafka 0.8
        properties.setProperty("zookeeper.connect", "192.168.20.32:2181");
        properties.setProperty("group.id", "test");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //读取kafka数据
        FlinkKafkaConsumer<String> producer = new FlinkKafkaConsumer<String>("test", new SimpleStringSchema(), properties);
        DataStreamSource<String> streamSource = env.addSource(producer);
        streamSource.addSink(new SinkFunction<String>() {
            @Override
            public void invoke(String value, Context context) throws Exception {
                System.out.println(value + " 规则集 ");
                KafkaProducer producer = new KafkaProducer<String, String>(properties);
                ProducerRecord<String, String> record = new ProducerRecord<>("sinktest", null, null, value);
                producer.send(record);
                producer.flush();
            }
        });
        env.execute();
    }


    /**
     * kafka写入数据
     *
     * @throws InterruptedException
     */
    @Test
    public void producer() throws InterruptedException {
        int i = 111111;
        while (true) {
            Thread.sleep(1000);
            execute_produce("走掉" + i ++);
        }
    }


    public void execute_produce(String content) {
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "192.168.20.32:9092");
        prop.setProperty("zookeeper.connect", "192.168.20.32:2181");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //key 序列化
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value 序列化
        KafkaProducer producer = new KafkaProducer<String, String>(prop);
        ProducerRecord<String, String> record = new ProducerRecord<>("sinktest", null, null, content);
        producer.send(record);
        producer.flush();
    }

}
