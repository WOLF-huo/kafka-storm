package com.example.demo;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SenqueceBolt extends BaseBasicBolt {

    public void execute(Tuple arg0, BasicOutputCollector arg1) {
        System.out.println(".....................................啊啊啊啊啊啊            啊啊啊啊啊啊啊啊啊啊啊 啊啊啊啊啊啊啊");
        System.out.println(arg0);
        String word = (String) arg0.getValue(0);
        String out = "output:" + word;
        System.out.println(out);

        //写文件
        try {
            System.out.println(".....................................啊啊啊啊啊啊            啊啊啊啊啊啊啊啊啊啊啊 啊啊啊啊啊啊啊");
            DataOutputStream out_file = new DataOutputStream(new FileOutputStream("kafkastorm.out"));
            out_file.writeUTF(out);
            out_file.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        arg1.emit(new Values(out));
    }

    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("message"));
    }

}