package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.log4j.Logger;

import java.util.*;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Abstract class SimpleKafka
 */
public abstract class AbstractSimpleKafka {
    /**
     * Instantiates a new Abstract class SimpleKafka.
     *
     * This abstract class's constructor provides graceful
     * shutdown behavior for Kafka producers and consumers
     */
    public AbstractSimpleKafka() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        log.info(MessageHelper.getSimpleJSONObject("Created the Shutdown Hook"));
    }
    private final Logger log = Logger.getLogger(AbstractSimpleKafka.class.getName());
    /**
     * Provides inherited classes will provide the behavior necessary
     * to shut down gracefully.
     * @throws Exception the exception
     */
    public abstract void shutdown() throws Exception;

}
