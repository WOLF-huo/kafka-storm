package com.example.demo;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class PrintBolt extends BaseBasicBolt {
    /**
     * execute会被storm一直调用
     * @param tuple
     * @param basicOutputCollector
     */
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        //为了便于查看消息用err标红
        System.out.println("开始了++++++++++++++++++++++++++++++++++++++++");
        System.out.println(tuple);
//        System.err.println(tuple.getValue(4));
//        System.err.println(tuple.getValues());
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}