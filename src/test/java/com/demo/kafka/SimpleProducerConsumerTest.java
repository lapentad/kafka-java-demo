package com.demo.kafka;

import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import static com.demo.kafka.PropertiesHelper.getProperties;

public class SimpleProducerConsumerTest {
    private final String fixedTopicName = "mycooltopic";
    @BeforeClass
    public void before_class() throws Exception {
        //String topicName = KafkaTopicHelper.createRandomTopic();
    }

    @Test
    public void canReadMessagesOnExistingTopicTest() throws Exception {
        String topicName = "2022-Feb-26kQ2QNzdSUI";
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        consumer.seekToBeginning(consumer.assignment());
        int recNum = 10;
        while (recNum > 0) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));
            if (records.count() == 0) {
                break;
            }

            for (ConsumerRecord<String, String> record : records) {
                new KafkaMessageTestHandlerImpl().processMessage(topicName, record);
                recNum--;
            }
        }

        consumer.close();
    }

    @Test
    public void canGetFixedTopic() throws Exception {
        //go in once
        TopicListing result1 = KafkaTopicHelper.createFixedTopic(fixedTopicName);
        //go in again
        TopicListing result2 = KafkaTopicHelper.createFixedTopic(fixedTopicName);
        Assert.assertNotNull(result1);
        Assert.assertNotNull(result2);
        Assert.assertEquals(result1.topicId(),result2.topicId());

    }

    @Test
    public void canProduceConsumeStreamTest() throws Exception {
        //Create a brand new topic
        KafkaTopicHelper.createFixedTopic(fixedTopicName);

        //Wait for Kafka to catch up with the topic creation before producing
        Thread.sleep(3000);

        //create messages
        int messageCount = 10;
        SimpleProducer producer = new SimpleProducer(fixedTopicName);
        producer.run(messageCount);

        //Wait for Kafka to catch up before consuming messages
        Thread.sleep(1000);

        //consume the messages
        SimpleConsumer.run(fixedTopicName, messageCount, new KafkaMessageTestHandlerImpl());
    }
}
