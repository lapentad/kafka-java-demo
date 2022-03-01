package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * The interface Kafka message handler.
 */
@FunctionalInterface
public interface KafkaMessageHandler {
    /**
     * Process message.
     *
     * @param topicName the topic name
     * @param message   the message
     * @throws Exception the exception
     */
    void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception;

}
