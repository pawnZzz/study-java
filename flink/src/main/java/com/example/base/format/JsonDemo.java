package com.example.base.format;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.formats.json.JsonDeserializationSchema;

/**
 * @author zyc
 */
public class JsonDemo {
    public static void main(String[] args) {
        JsonDeserializationSchema<Data> jsonFormat = new JsonDeserializationSchema<>(Data.class);

        KafkaSourceBuilder<Data> objectKafkaSourceBuilder = KafkaSource.<Data>builder()
                .setValueOnlyDeserializer(jsonFormat);
    }
}

