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

package com.demo.kafka.messages;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * The interface KafkaMessageHandler.
 *
 * This interface is the template callback functions that can
 * be passed to an instance of the {@link com.demo.kafka.consumer.SimpleConsumer}
 */
@FunctionalInterface
public interface KafkaMessageHandler {
    /**
     * The method that defines the message processing behavior
     *
     * @param topicName The name of the topic being consumed
     * @param message The message that was consumed
     * @throws Exception Thrown if an exception occurs
     */
    void processMessage(String topicName, ConsumerRecord<String, String> message) throws Exception;
}
