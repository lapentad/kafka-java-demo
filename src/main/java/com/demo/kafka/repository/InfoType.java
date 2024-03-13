package com.demo.kafka.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
public class InfoType {

    @Getter
    private String id;

    @Getter
    private String kafkaInputTopic;

    @Getter
    private String inputJobType;

    @Getter
    private Object inputJobDefinition;

}
