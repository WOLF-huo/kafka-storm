package com.example.demo;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class MessageScheme implements Scheme {

    public List<Object> deserialize(ByteBuffer arg0) {
        Charset charset = Charset.forName("UTF-8");
        String msg = charset.decode(arg0).toString();
        return new Values(msg);
    }

    public Fields getOutputFields() {
        return new Fields("msg");
    }

}