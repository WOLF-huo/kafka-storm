package com.example.demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
public class WordCountTopolopgyAllInJava {
    // 定义一个喷头，用于产生数据。该类继承自BaseRichSpout
    public static class RandomSentenceSpout extends BaseRichSpout {
        SpoutOutputCollector _collector;
        Random _rand;
        private AtomicInteger counter;

        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            _collector = collector;
            _rand = new Random();
            counter = new AtomicInteger();
        }

        public void nextTuple() {

            // 睡眠一段时间后再产生一个数据
            Utils.sleep(100);

            // 句子数组
            String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
                    "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};

            // 随机选择一个句子
            String sentence = sentences[_rand.nextInt(sentences.length)];

            // 发射该句子给Bolt
            _collector.emit(new Values(sentence), this.counter.getAndIncrement());//发送加id才能反馈ack
        }

        // 确认函数
        @Override
        public void ack(Object id) {
            System.out.println("success! id: " + id.toString());
        }

        // 处理失败的时候调用
        @Override
        public void fail(Object id) {
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            // 定义一个字段word
            declarer.declare(new Fields("word"));
        }
    }
    // 定义个Bolt，用于将句子切分为单词
    public static class SplitSentence extends BaseBasicBolt {

        public void execute(Tuple tuple, BasicOutputCollector collector){
            // 接收到一个句子
            String sentence = tuple.getString(0);
            // 把句子切割为单词
            StringTokenizer iter = new StringTokenizer(sentence);
            // 发送每一个单词
            while(iter.hasMoreElements()){
                collector.emit(new Values(iter.nextToken()));
            }
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer){
            // 定义一个字段
            declarer.declare(new Fields("word"));
        }
    }
    // 定义一个Bolt，用于单词计数
    public static class WordCount extends BaseBasicBolt {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        public void execute(Tuple tuple, BasicOutputCollector collector){
            // 接收一个单词
            String word = tuple.getString(0);
            // 获取该单词对应的计数
            Integer count = counts.get(word);
            if(count == null)
                count = 0;
            // 计数增加
            count++;
            // 将单词和对应的计数加入map中
            counts.put(word,count);
            System.out.println("hello word!");
            System.out.println(word +"  "+count);
            // 发送单词和计数（分别对应字段word和count）
            collector.emit(new Values(word, count));
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer){
            // 定义两个字段word和count
            declarer.declare(new Fields("word","count"));
        }
    }
    public static void main(String[] args) throws Exception
    {
        // 创建一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        // 设置Spout，这个Spout的名字叫做"Spout"，设置并行度为5，增加executor线程数目
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        // 设置slot——“split”，并行度为8，它的数据来源是spout的
        builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
        // 设置slot——“count”,你并行度为12，它的数据来源是split的word字段
        builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(false);
        conf.setMaxTaskParallelism(20);//topology 最大并行度


        if (args != null && args.length > 0) {
            //集群运行
            StormSubmitter.submitTopology(args[0],conf,builder.createTopology());
        } else {
            //本地运行
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            Thread.sleep(10000);
        }
    }
}