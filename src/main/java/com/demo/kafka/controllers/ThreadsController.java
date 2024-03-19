/*-
 * ========================LICENSE_START=================================
 * O-RAN-SC
 * 
 * Copyright (C) 2024 Nordix Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================LICENSE_END===================================
 */

package com.demo.kafka.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kafka.consumer.SimpleConsumer;
import com.demo.kafka.messages.ApplicationMessageHandlerImpl;
import com.demo.kafka.producer.SimpleProducer;

@RestController
public class ThreadsController {

    @Autowired
    private SimpleProducer simpleProducer;

    @Autowired
    private SimpleConsumer simpleConsumer;

    @Async
    @GetMapping("/startProducer/{topicName}")
    public CompletableFuture<String> startProducer(@PathVariable("topicName") String topicName) {
        try {
            Thread thread = new Thread(() -> {
                try {
                    simpleProducer.runAlways(topicName, new ApplicationMessageHandlerImpl());
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
}
