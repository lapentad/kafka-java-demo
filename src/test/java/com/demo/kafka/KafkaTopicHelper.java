package com.demo.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import com.demo.kafka.messages.MessageHelper;
import com.demo.kafka.messages.PropertiesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KafkaTopicHelper is a class the provides helper functions
 * related to a Kafka topic
 */
public class KafkaTopicHelper {

    /**
     * Creates a topic according to the name provided by the
     * parameter topicName
     *
     * @param topicName the name of the topic to create
     * @return a {@link org.apache.kafka.clients.admin.TopicListing} object
     * @throws Exception the exception
     */
    public static TopicListing createFixedTopic(String topicName) throws Exception {

        Properties props = PropertiesHelper.getProperties();
        Admin admin = Admin.create(props);
        // if the topic exists, if not make it
        ListTopicsResult topics = admin.listTopics();

        for (TopicListing listing : topics.listings().get()) {
            if (new String(listing.name()).equals(topicName))
                return listing;
        }
        int partitions = 1;
        short replicationFactor = 1;
        NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

        CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

        KafkaFuture<Void> future = result.values().get(topicName);
        future.get();

        topics = admin.listTopics();

        for (TopicListing listing : topics.listings().get()) {
            if (new String(listing.name()).equals(topicName))
                return listing;
        }

        return null;
    }

    /**
     * Create random topic string.
     *
     * @return the string
     * @throws Exception the exception
     */
    public static String createRandomTopic() throws Exception {

        Properties props = PropertiesHelper.getProperties();
        Admin admin = Admin.create(props);
        String newTopicName = getCurrentUtcTimestamp() + MessageHelper.getRandomString();
        int partitions = 1;
        short replicationFactor = 1;
        NewTopic newTopic = new NewTopic(newTopicName, partitions, replicationFactor);

        CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

        KafkaFuture<Void> future = result.values().get(newTopicName);
        future.get();

        return newTopicName;
    }

    private static String getCurrentUtcTimestamp() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date());
    }
}
