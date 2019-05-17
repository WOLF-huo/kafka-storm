package com.example.demo;

import java.util.*;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.*;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Time;
import org.apache.storm.utils.Utils;
import org.apache.storm.kafka.spout.KafkaSpout;

public class StormKafkaTopo {
    public static void main(String[] args) {
        BrokerHosts brokerHosts = new ZkHosts("192.168.112.132:2189");

        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "mytest", "/kafka", "kafkaSpout");
        Config conf = new Config();
        Map<String, String> map = new HashMap<String, String>();

        map.put("metadata.broker.list", "192.168.112.132:9092");
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        conf.put("topic", "topic2");

        TopologyBuilder builder = new TopologyBuilder();
        KafkaSpoutConfig.Builder<String, String> kafkaBuilder = KafkaSpoutConfig.builder("192.168.112.132:9092,192.168.112.133:9092,192.168.112.134:9092", "mytest");
        //设置kafka属于哪个组
        kafkaBuilder.setGroupId("testgroup");
        //创建kafkaspoutConfig
        KafkaSpoutConfig<String, String> build = kafkaBuilder.build();
        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<String,String>(build);
        builder.setSpout("spout", kafkaSpout);
        builder.setBolt("bolt", new SenqueceBolt()).shuffleGrouping("spout");

        if(args != null && args.length > 0) {
            //提交到集群运行
            try {
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
                System.out.println("睡觉zzz");
                try {
                    Time.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            } catch (AlreadyAliveException e) {
                System.out.println("111111111");
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                System.out.println("2222222222");
                e.printStackTrace();
            } catch (AuthorizationException e){
                System.out.println("3333333333");
                e.printStackTrace();
            }
        } else {
            //本地模式运行
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Topotest1121", conf, builder.createTopology());
            Utils.sleep(1000000);
            cluster.killTopology("Topotest1121");
            cluster.shutdown();
        }



    }
}