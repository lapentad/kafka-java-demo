package com.demo.kafka;

import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;


class SimpleProducer extends AbstractSimpleKafka {
    public SimpleProducer(String topicName) throws Exception {
        super();
        setTopicName(topicName);
    }

    private KafkaProducer<String, String> kafkaProducer;
    private final AtomicBoolean closed = new AtomicBoolean(false);


    private final Logger log = Logger.getLogger(SimpleProducer.class.getName());
    public void run(int numberOfMessages) throws Exception {
        int i = 0;
        while (i <= numberOfMessages) {
            String key = UUID.randomUUID().toString();
            String message = MessageHelper.getRandomString();
            this.send(key, message);
            i++;
            Thread.sleep(100);
        }
        this.shutdown();

    }
    public void runAlways() throws Exception {
        while (true) {
            String key = UUID.randomUUID().toString();
            String message = MessageHelper.getRandomString();
            this.send(key, message);
            Thread.sleep(100);
        }
    }

    private String topicName = null;

    private void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private String getTopicName() {
        return this.topicName;
    }


    protected void send(String key, String message) throws Exception {
        String topicName = this.getTopicName();
        String source = SimpleProducer.class.getName();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.getTopicName(), key, message);
        JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName, key, message);
        log.info(obj.toJSONString());
        getKafkaProducer().send(producerRecord);
    }

    private KafkaProducer<String, String> getKafkaProducer() throws Exception {
        if (this.kafkaProducer == null) {
            Properties props = PropertiesHelper.getProperties();
            this.kafkaProducer = new KafkaProducer<>(props);
        }
        return this.kafkaProducer;
    }

    /*
    private void setKafkaProducer(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
     */


    public void shutdown() throws Exception {
        closed.set(true);
        log.info(MessageHelper.getSimpleJSONObject("Shutting down producer"));
        getKafkaProducer().close();
    }
}
