package com.demo.kafka.r1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "consumer_job", description = "Information for an Information Job")
public class ConsumerJobInfo {

    @Schema(name = "info_type_id", description = "Information type Idenitifier of the subscription job")
    @SerializedName("info_type_id")
    @JsonProperty(value = "info_type_id", required = true)
    public String infoTypeId = "";

    @Schema(name = "job_owner", description = "Identity of the owner of the job")
    @SerializedName("job_owner")
    @JsonProperty(value = "job_owner", required = true)
    public String owner = "";

    @Schema(name = "job_definition", description = "Information type specific job data")
    @SerializedName("job_definition")
    @JsonProperty(value = "job_definition", required = true)
    public Object jobDefinition;

    @Schema(name = "job_result_uri", description = "The target URI of the subscribed information")
    @SerializedName("job_result_uri")
    @JsonProperty(value = "job_result_uri", required = true)
    public String jobResultUri = "";

    @Schema(name = "status_notification_uri",
            description = "The target of Information subscription job status notifications")
    @SerializedName("status_notification_uri")
    @JsonProperty(value = "status_notification_uri", required = false)
    public String statusNotificationUri = "";

    public ConsumerJobInfo() {}

    public ConsumerJobInfo(String infoTypeId, Object jobData, String owner, String statusNotificationUri) {
        this.infoTypeId = infoTypeId;
        this.jobDefinition = jobData;
        this.owner = owner;
        this.statusNotificationUri = statusNotificationUri;
    }
}
