package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static com.demo.kafka.PropertiesHelper.getProperties;

/**
 * The type SimpleConsumer is a class the demonstrates how to consume messages
 * from a Kafka cluster.
 */
class SimpleConsumer {
    private static final int TIME_OUT_MS = 5000;

    /**
     * The class's Log4J logger
     */
    static Logger log = Logger.getLogger(SimpleConsumer.class.getName());

    /**
     * The run method retrieves a collection of ConsumerRecords. The number of
     * ConsumerRecords retrieved is defined according to max.poll.records which
     * is defined in the file, config.properties
     *
     * @param topic    the topic to access
     * @param callback the callback function that processes messages retrieved
     *                 from Kafka
     * @param numberOfRecords Optional, the max number of records to retrieve during the
     *                        run to the consumer. If nul, the  number of records will be that
     *                        value defined in max.poll.records as defined in config.properties
     * @throws Exception the Exception that will get thrown upon an error
     */
    static void run(String topic, KafkaMessageHandler callback, Integer numberOfRecords) throws Exception {
        Properties props = getProperties();
        //See if the number of records is provided
        Optional<Integer> recs = Optional.ofNullable(numberOfRecords);

        //adjust the number of records to get accordingly
        Integer numOfRecs = recs.orElseGet(() -> Integer.parseInt(props.getProperty("max.poll.records")));
        props.setProperty("max.poll.records", String.valueOf(numOfRecs));

        // create the consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.assign(Collections.singleton(new TopicPartition(topic, 0)));

        int recNum = numOfRecs;
        while (recNum > 0) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(TIME_OUT_MS));
            recNum = records.count();
            if (recNum == 0) {
                log.info(MessageHelper.getSimpleJSONObject("No records retrieved"));
                break;
            }

            for (ConsumerRecord<String, String> record : records) {
                callback.processMessage(topic, record);
                recNum--;
            }
        }

        consumer.close();
    }

    /**
     * Run always.
     *
     * @param topic    the topic
     * @param callback the callback
     * @throws Exception the exception
     */
    static void runAlways(String topic, KafkaMessageHandler callback) throws Exception {
        Properties props = getProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.assign(Collections.singleton(new TopicPartition(topic, 0)));
        int recNum = Integer.parseInt(props.getProperty("max.poll.records"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(TIME_OUT_MS));
            if (records.count() == 0) {
                log.info(MessageHelper.getSimpleJSONObject("No records retrieved"));
            }

            for (ConsumerRecord<String, String> record : records) {
                callback.processMessage(topic, record);
            }
        }

        //consumer.close();
    }
}
