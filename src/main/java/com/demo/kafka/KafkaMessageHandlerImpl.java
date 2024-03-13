package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;

/**
 * An implementation of the interface {@link com.demo.kafka.KafkaMessageHandler}.
 *
 * The class implements the processMessage() method. Typically, this class is used
 * to supply callback behavior for this project's producers and consumers.
 */
public class KafkaMessageHandlerImpl implements KafkaMessageHandler{
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageHandlerImpl.class);

    @Override
    public void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception {
        String source = KafkaMessageHandlerImpl.class.getName();
        JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName,message.key(),message.value());
        log.info(obj.toJSONString());
    }
}
