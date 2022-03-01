package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class Application {
    private static final int NUM_OF_RECORD = 10;

    private static class ApplicationMessageHandlerImpl implements KafkaMessageHandler{

        static Logger log = Logger.getLogger(ApplicationMessageHandlerImpl.class.getName());

        @Override
        public void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception {
            String source = KafkaMessageHandlerImpl.class.getName();
            JSONObject obj = DataHelper.getMessageLogEntryJSON(source, topicName,message.key(),message.value());
            System.out.println(obj.toJSONString());
            log.info(obj.toJSONString());
        }
    }

    public static void main(String[] args) throws Exception {
        String mode = args[0];
        String topic = args[1];
        String numOfRecs = args[2];

        switch(mode.toLowerCase(Locale.ROOT)) {
            case "producer":
                System.out.println("Starting the Producer\n");
                new SimpleProducer(topic).run(Integer.parseInt(numOfRecs));
                break;
            case "consumer":
                System.out.println("Starting the Consumer\n");
                SimpleConsumer.run(topic, Integer.parseInt(numOfRecs), new ApplicationMessageHandlerImpl() );
                break;
            default:
                System.out.println("ERROR: You need to declare the first parameter as Producer or Consumer,\n" +
                        "the second parameter is the topic name,\n " +
                        "the third parameter is the number of records to process");
        }


    }
}
