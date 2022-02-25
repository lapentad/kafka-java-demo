package com.demo.kafka;

import com.demo.kafka.DataHelper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;



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
            String message = DataHelper.getRandomString();
            this.send(key, message);
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

    protected void send(String key, String message) throws Exception {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(this.getTopicName(), key, message);
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


}
