package com.demo.kafka.r1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "producer_info_type_info", description = "Information for an Information Type")
public class ProducerInfoTypeInfo {

    @Schema(name = "info_job_data_schema", description = "Json schema for the job data", required = true)
    @SerializedName("info_job_data_schema")
    @JsonProperty(value = "info_job_data_schema", required = true)
    public Object jobDataSchema;

    @Schema(name = "info_type_information", description = "Type specific information for the information type",
            required = true)
    @SerializedName("info_type_information")
    @JsonProperty(value = "info_type_information", required = true)
    public Object typeSpecificInformation;

    public ProducerInfoTypeInfo(Object jobDataSchema, Object typeSpecificInformation) {
        this.jobDataSchema = jobDataSchema;
        this.typeSpecificInformation = typeSpecificInformation;
    }

    public ProducerInfoTypeInfo() {}

}
