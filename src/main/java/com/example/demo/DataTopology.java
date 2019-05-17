package com.example.demo;

import org.apache.storm.Config;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataTopology {

//        String topic = "data" ;
//        ZkHosts zkHosts = new ZkHosts("192.168.112.132:2189");
//        SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic,
//                "",
//                "MyTrack") ;
//        List<String> zkServers = new ArrayList<String>() ;
//        zkServers.add("192.168.59.132");
//        spoutConfig.zkServers = zkServers;
//        spoutConfig.zkPort = 2181;
//        spoutConfig.socketTimeoutMs = 60 * 1000 ;
//        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme()) ;
//
//        TopologyBuilder builder = new TopologyBuilder() ;
//        builder.setSpout("spout", new KafkaSpout(spoutConfig) ,1) ;
//        builder.setBolt("bolt1", new MyKafkaBolt(), 1).shuffleGrouping("spout") ;
//
//        Config conf = new Config ();
//        conf.setDebug(false) ;
//
//        if (args.length > 0) {
//            try {
//                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
//            } catch (AlreadyAliveException e) {
//                e.printStackTrace();
//            } catch (InvalidTopologyException e) {
//                e.printStackTrace();
//            }
//        }else {
//            LocalCluster localCluster = new LocalCluster();
//            localCluster.submitTopology("mytopology", conf, builder.createTopology());
//        }
//
//    }
}
