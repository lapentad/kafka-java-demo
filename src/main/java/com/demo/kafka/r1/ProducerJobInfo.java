package com.demo.kafka.r1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "producer_info_job_request",
        description = "The body of the Information Producer callbacks for Information Job creation and deletion")
public class ProducerJobInfo {

    @Schema(name = "info_job_identity", description = "Identity of the Information Job", required = true)
    @SerializedName("info_job_identity")
    @JsonProperty("info_job_identity")
    public String id = "";

    @Schema(name = "info_type_identity", description = "Type identity for the job")
    @SerializedName("info_type_identity")
    @JsonProperty("info_type_identity")
    public String typeId = "";

    @Schema(name = "info_job_data", description = "Json for the job data")
    @SerializedName("info_job_data")
    @JsonProperty("info_job_data")
    public Object jobData;

    @Schema(name = "owner", description = "The owner of the job")
    @SerializedName("owner")
    @JsonProperty("owner")
    public String owner = "";

    @Schema(name = "last_updated", description = "The time when the job was last updated or created (ISO-8601)")
    @SerializedName("last_updated")
    @JsonProperty("last_updated")
    public String lastUpdated = "";

    public ProducerJobInfo(Object jobData, String id, String typeId, String owner, String lastUpdated) {
        this.id = id;
        this.jobData = jobData;
        this.typeId = typeId;
        this.owner = owner;
        this.lastUpdated = lastUpdated;
    }

    public ProducerJobInfo() {}

}
