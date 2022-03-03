package com.demo.kafka;

import org.apache.kafka.clients.admin.TopicListing;
import org.testng.Assert;
import org.testng.annotations.Test;



public class SimpleProducerConsumerTest {
    private final String fixedTopicName = "mycooltopic";

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
    public void canProduceLimitConsumeStreamByDefinitionTest() throws Exception {
        //Create a topic
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
        new SimpleConsumer().run(fixedTopicName, new KafkaMessageTestHandlerImpl(), 4);
    }


    @Test
    public void canProduceLimitConsumeStreamByDefaultTest() throws Exception {
        //Create a topic
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
        new SimpleConsumer().run(fixedTopicName, new KafkaMessageTestHandlerImpl(), null);
    }

    @Test
    public void canProduceConsumeAlwaysStreamTest() throws Exception {
        KafkaTopicHelper.createFixedTopic(fixedTopicName);

        //Wait for Kafka to catch up with the topic creation before producing
        Thread.sleep(3000);

        //create messages
        int messageCount = 20;
        SimpleProducer producer = new SimpleProducer(fixedTopicName);
        producer.run(messageCount);

        //Wait for Kafka to catch up before consuming messages
        Thread.sleep(1000);

        //create the consumer
        SimpleConsumer consumer = new SimpleConsumer();
        //consumer.runAlways(fixedTopicName, new KafkaMessageTestHandlerImpl());
    }
}
