package com.demo.kafka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

import com.demo.kafka.r1.ProducerJobInfo;
import com.demo.kafka.repository.InfoTypes;
import com.demo.kafka.repository.Jobs;
import com.demo.kafka.repository.Job.Parameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ApplicationController {

    private static Gson gson = new GsonBuilder().create();

    @Autowired
    private SimpleProducer simpleProducer;

    @Autowired
    private SimpleConsumer simpleConsumer;

    private final Jobs jobs;
    private final InfoTypes types;
    public ApplicationController(@Autowired Jobs jobs, @Autowired InfoTypes types) {
        this.jobs = jobs;
        this.types = types;
    }

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
    @GetMapping("/startConsumer/{topicName}")
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

    @PostMapping("/{infoJobId}")
    public void jobCallback(@RequestBody String requestBody, @PathVariable String infoJobId) {
        ProducerJobInfo request = gson.fromJson(requestBody, ProducerJobInfo.class);
        try {
            this.jobs.addJob(request.id, types.getType(request.typeId), request.owner, request.lastUpdated,
                toJobParameters(request.jobData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parameters toJobParameters(Object jobData) {
        String json = gson.toJson(jobData);
        return gson.fromJson(json, Parameters.class);
    }

}
