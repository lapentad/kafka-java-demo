package com.demo.kafka;

import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;


/**
 * The type SimpleProducer is a wrapper class for {@link org.apache.kafka.clients.producer.KafkaProducer}.
 * The object publishes methods that send messages that have random string
 * content onto the Kafka broker defined in {@link /src/resources/config.properties}
 */
class SimpleProducer extends AbstractSimpleKafka {
    /**
     * Instantiates a new SimpleProducer object.
     *
     * @param topicName the topic name
     * @throws Exception the exception
     */
    public SimpleProducer(String topicName) throws Exception {
        super();
        setTopicName(topicName);
    }

    private KafkaProducer<String, String> kafkaProducer;
    private final AtomicBoolean closed = new AtomicBoolean(false);


    private final Logger log = Logger.getLogger(SimpleProducer.class.getName());

    /**
     * This method sends a limited number of messages
     * with random string data to the Kafka broker
     *
     * @param numberOfMessages the number of messages to send
     * @throws Exception the exception that gets raised upon error
     */
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

    /**
     * The method runAlways() continuously creates and
     * sends messages that contain random string data
     * to the Kafka broker.
     *
     * @throws Exception the exception that get raised on error
     */
    public void runAlways() throws Exception {
        while (true) {
            String key = UUID.randomUUID().toString();
            //use the Message Helper to get a random string
            String message = MessageHelper.getRandomString();
            //send the message
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


    /**
     * Does the work of sending a message to
     * a Kafka broker. The method uses the name of
     * the topic that was declared in this class's
     * constructor.
     *
     * @param key     the key value for the message
     * @param message the content of the message
     * @throws Exception the exception that gets thrown upon error
     */
    protected void send(String key, String message) throws Exception {
        String topicName = this.getTopicName();
        String source = SimpleProducer.class.getName();

        //create the ProducerRecord object which will
        //represent the message to the Kafka broker.
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(topicName, key, message);

        //Use the helper to create an informative log entry in JSON format
        JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName, key, message);
        log.info(obj.toJSONString());
        //Send the message to the Kafka broker using the internal
        //KafkaProducer
        getKafkaProducer().send(producerRecord);
    }

    private KafkaProducer<String, String> getKafkaProducer() throws Exception {
        if (this.kafkaProducer == null) {
            Properties props = PropertiesHelper.getProperties();
            this.kafkaProducer = new KafkaProducer<>(props);
        }
        return this.kafkaProducer;
    }

    public void shutdown() throws Exception {
        closed.set(true);
        log.info(MessageHelper.getSimpleJSONObject("Shutting down producer"));
        getKafkaProducer().close();
    }
}
