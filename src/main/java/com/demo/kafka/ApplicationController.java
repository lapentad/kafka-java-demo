package com.demo.kafka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

@RestController
public class ApplicationController {

    @Autowired
    private SimpleProducer simpleProducer;

    @Autowired
    private SimpleConsumer simpleConsumer;

    @GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    @Async
    @GetMapping("/startProducer/{topicName}")
    public CompletableFuture<String> startProducer(@PathVariable("topicName") String topicName) {
        try {
            Thread thread = new Thread(() -> {
                try {
                    simpleProducer.runAlways(topicName,new ApplicationMessageHandlerImpl());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return CompletableFuture.completedFuture("Producer started for topic: " + topicName);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    @GetMapping("/startProducer/{topicName}")
    public CompletableFuture<String> startConsumer(@PathVariable("topicName") String topicName) {
        try {
            Thread thread = new Thread(() -> {
                try {
                    simpleConsumer.runAlways(topicName, new ApplicationMessageHandlerImpl());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return CompletableFuture.completedFuture("Consumer started for topic: " + topicName);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
