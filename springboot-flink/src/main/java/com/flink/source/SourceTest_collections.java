package com.flink.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


/**
 * @Author kangjiwei
 * @Date 2020/12/10
 */
public class SourceTest_collections {


    /**
     * 读取集合
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






}
