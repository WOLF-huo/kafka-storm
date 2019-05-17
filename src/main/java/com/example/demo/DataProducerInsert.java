package com.example.demo;



import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class DataProducerInsert extends  Thread {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.112.132:9092,192.168.112.132:9093,192.168.112.132:9094");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");//16M
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /**
         * 两个泛型参数
         * 第一个泛型参数：指的就是kafka中一条记录key的类型
         * 第二个泛型参数：指的就是kafka中一条记录value的类型
         */
        String[] girls = new String[]{"姚慧莹", "刘向前", "周  新", "杨柳"};
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        String topic = "mytest";//props.getProperty(Constants.KAFKA_PRODUCER_TOPIC);
        String key = "1";
        String value = "今天的姑娘们很美";
        for(int i=0; i < 10; i++){
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<String, String>(topic, key, value+i+UUID.randomUUID());
            producer.send(producerRecord);
            System.out.println("send message: " + "hello kafka" + i);
        }
        producer.close();
    }
}
