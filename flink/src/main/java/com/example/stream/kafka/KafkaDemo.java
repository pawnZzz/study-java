package com.example.stream.kafka;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.shaded.guava30.com.google.common.collect.Maps;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * @author zyc
 */
public class KafkaDemo {
    public static void main(String[] args) throws Exception {
        String brokers = "";
        String topic = "";
        String consumerGroup = "";

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(brokers)
                .setTopics(topic)
                .setGroupId(consumerGroup)
                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer.class))
                .setBounded(OffsetsInitializer.offsets(Maps.newHashMap())) // stream模式时设置停止的位置
                .build();

        DataStreamSource<String> dSource = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        dSource.print();

        env.execute();
    }
}
