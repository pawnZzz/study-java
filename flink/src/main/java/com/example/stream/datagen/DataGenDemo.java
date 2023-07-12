package com.example.stream.datagen;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.functions.source.datagen.RandomGenerator;

import java.util.Random;

/**
 * @author zyc
 */
public class DataGenDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataGeneratorSource<String> genSource = new DataGeneratorSource<>(new RandomGenerator<String>() {
            @Override
            public String next() {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                Random random = new Random();
                StringBuilder sb = new StringBuilder(10);

                for (int i = 0; i < 10; i++) {
                    int randomIndex = random.nextInt(characters.length());
                    char randomChar = characters.charAt(randomIndex);
                    sb.append(randomChar);
                }

                return sb.toString();
            }
        }, 10, 10000L);

        DataStreamSource<String> source = env.addSource(genSource, Types.STRING);

        source.print();

        env.execute();
    }
}
