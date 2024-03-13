package com.demo.kafka.r1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collection;

import lombok.Builder;

@Builder
@Schema(name = "producer_registration_info", description = "Information for an Information Producer")
public class ProducerRegistrationInfo {

    @Schema(name = "supported_info_types", description = "Supported Information Type IDs", required = true)
    @SerializedName("supported_info_types")
    @JsonProperty(value = "supported_info_types", required = true)
    public Collection<String> supportedTypeIds;

    @Schema(name = "info_job_callback_url", description = "callback for Information Job", required = true)
    @SerializedName("info_job_callback_url")
    @JsonProperty(value = "info_job_callback_url", required = true)
    public String jobCallbackUrl;

    @Schema(name = "info_producer_supervision_callback_url", description = "callback for producer supervision",
            required = true)
    @SerializedName("info_producer_supervision_callback_url")
    @JsonProperty(value = "info_producer_supervision_callback_url", required = true)
    public String producerSupervisionCallbackUrl;

    public ProducerRegistrationInfo(Collection<String> types, String jobCallbackUrl,
            String producerSupervisionCallbackUrl) {
        this.supportedTypeIds = types;
        this.jobCallbackUrl = jobCallbackUrl;
        this.producerSupervisionCallbackUrl = producerSupervisionCallbackUrl;
    }

    public ProducerRegistrationInfo() {}

}
