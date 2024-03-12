package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ApplicationMessageHandlerImpl implements KafkaMessageHandler{

    private static final Logger log = LoggerFactory.getLogger(ApplicationMessageHandlerImpl.class);

    @Override
    public void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception {
        String source = KafkaMessageHandlerImpl.class.getName();
        JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName,message.key(),message.value());
        System.out.println(obj.toJSONString());
        log.info(obj.toJSONString());
    }
}
