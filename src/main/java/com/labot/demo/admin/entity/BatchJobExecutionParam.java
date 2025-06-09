package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_PARAMS")
@Data
public class BatchJobExecutionParam {
    @EmbeddedId
    private JobExecutionParamId id;

    @ManyToOne
    @MapsId("jobExecutionId")
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private BatchJobExecution jobExecution;

    @Column(name = "PARAMETER_VALUE", length = 2500)
    private String parameterValue;

    @Column(name = "IDENTIFYING")
    private String identifying;
}
