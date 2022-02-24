package com.demo.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;

import static com.demo.kafka.PropertiesHelper.getProperties;

class SimpleConsumer {
    private static final int TIME_OUT_MS = 5000;

    static void run(String topic, int numOfRecords) throws Exception {
        Properties props = getProperties();

        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(props);
        consumer.subscribe(Collections.singletonList(topic));

        while (numOfRecords > 0) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(TIME_OUT_MS);
            for (ConsumerRecord<byte[], byte[]> record : records) {
                String position = record.partition() + "-" + record.offset();
                String key = new String(record.key(), StandardCharsets.UTF_8);
                String value = new String(record.value(), StandardCharsets.UTF_8);
                System.out.println(position + ": " + key + " " + value);
            }
            numOfRecords -= records.count();
        }

        consumer.close();
    }
}
