package com.demo.kafka.repository;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Job {
    @Builder
    public static class Parameters {

        @Builder
        @EqualsAndHashCode
        public static class KafkaDeliveryInfo {
            @Getter
            private String topic;

            @Getter
            private String bootStrapServers;
        }

        @Getter
        private KafkaDeliveryInfo deliveryInfo;
    }

    @Getter
    private final String id;

    @Getter
    private final InfoType type;

    @Getter
    private final String owner;

    @Getter
    private final Parameters parameters;

    @Getter
    private final String lastUpdated;

    public Job(String id, InfoType type, String owner, String lastUpdated, Parameters parameters) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.lastUpdated = lastUpdated;
        this.parameters = parameters;
    }
}
