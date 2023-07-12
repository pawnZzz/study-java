package com.example.base.format;

import org.apache.flink.formats.avro.AvroInputFormat;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zyc
 */
public class AvroDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /*AvroInputFormat<User> users = new AvroInputFormat<User>(in, User.class);
        DataStream<User> usersDS = env.createInput(users);*/
    }
}

class User{

}
