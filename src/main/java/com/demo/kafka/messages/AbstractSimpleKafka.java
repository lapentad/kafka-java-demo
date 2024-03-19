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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Abstract class SimpleKafka
 */
public abstract class AbstractSimpleKafka {
    private static final Logger log = LoggerFactory.getLogger(AbstractSimpleKafka.class);

    /**
     * Instantiates a new Abstract class, SimpleKafka.
     * <p>
     * This abstract class's constructor provides graceful
     * shutdown behavior for Kafka producers and consumers
     *
     * @throws Exception the exception
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

        log.info(MessageHelper.getSimpleJSONObject("Created the Shutdown Hook").toJSONString());
    }

    /**
     * The inherited classes will provide the behavior necessary
     * to shut down gracefully.
     *
     * @throws Exception the exception that get thrown upon error
     */
    public abstract void shutdown() throws Exception;

    /**
     * This purpose of this method is to provide continuous
     * behavior to produce or consume messages from a Kafka
     * broker
     *
     * @param topicName the topicName to execute against
     * @param callback a callback function to provide processing
     *        logic after a message is produced or after
     *        a message is consumed
     * @throws Exception the exception that get thrown upon error
     */
    public abstract void runAlways(String topicName, KafkaMessageHandler callback) throws Exception;
}
