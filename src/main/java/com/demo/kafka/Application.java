package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    //private static final int NUM_OF_RECORD = 10;

    private static class ApplicationMessageHandlerImpl implements KafkaMessageHandler{

        private static final Logger log = LoggerFactory.getLogger(ApplicationMessageHandlerImpl.class);

        @Override
        public void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception {
            String source = KafkaMessageHandlerImpl.class.getName();
            JSONObject obj = MessageHelper.getMessageLogEntryJSON(source, topicName,message.key(),message.value());
            System.out.println(obj.toJSONString());
            log.info(obj.toJSONString());
        }
    }

    //private SimpleProducer producer;
    //private SimpleConsumer consumer;
    //private ApplicationMessageHandlerImpl messageHandler = new ApplicationMessageHandlerImpl();


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    
    @GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    @GetMapping("/startProducer/{topicName}")
    public String startProducer(@PathVariable("topicName") String topicName) throws Exception {
        new SimpleProducer().runAlways(topicName, new ApplicationMessageHandlerImpl());
        return "Producer started for topic: " + topicName;
    }


    @GetMapping("/startConsumer/{topicName}")
    public String startConsumer(@PathVariable("topicName") String topicName) throws Exception {
        var consumer = new SimpleConsumer();
        var messageHandler = new ApplicationMessageHandlerImpl(); 
        consumer.runAlways(topicName, messageHandler);
        return "Consumer started for topic: " + topicName; 
    }
}
