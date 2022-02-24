package com.demo.kafka;

import com.demo.kafka.PropertiesHelper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;


import static com.demo.kafka.PropertiesHelper.getProperties;
import static java.lang.Thread.sleep;

class SimpleProducer {

    public SimpleProducer(String topicName) {
        setTopicName(topicName);
    }

    private KafkaProducer<String, String> kafkaProducer;

    public void run() throws Exception {
        int i = 0;
        while (true) {
            String topicName = getTopicName();
            String key = String.valueOf(i);
            String message = getRandomString();
            this.send(topicName, key, message);
            i++;
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

    private void send(String topicName, String key, String message) throws Exception {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, key, message);
        getKafkaProducer().send(producerRecord);
    }

    public void close() {
        try {
            getKafkaProducer().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private KafkaProducer<String, String> getKafkaProducer() throws Exception {
        if (this.kafkaProducer == null) {
            Properties props = getProperties();
            this.kafkaProducer = new KafkaProducer<String, String>(props);
        }
        return this.kafkaProducer;
    }

    private void setKafkaProducer(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
