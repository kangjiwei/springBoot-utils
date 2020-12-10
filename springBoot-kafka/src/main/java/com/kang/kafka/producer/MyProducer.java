package com.kang.kafka.producer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

public class MyProducer {

    /*
    *
    * bn-kfk-p:  Connection with localhost/127.0.0.1 disconnected
    * 设置为kafka服务器ip,但是访问确实以上提示。
    * bn-kfk-a:  kafka server.properties 添加配置
    * # 允许外部端口连接 listeners=PLAINTEXT://:9092
    * # 外部代理地址  advertised.listeners=PLAINTEXT://192.168.130.130:9092
    * */
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.104:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
            System.out.println(i + " 发送！ ");
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
        }
        System.out.println("发送完毕！");
        producer.close();
    }


}
