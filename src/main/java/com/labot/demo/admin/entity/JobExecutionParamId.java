package com.labot.demo.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class JobExecutionParamId implements java.io.Serializable {
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;

    @Column(name = "PARAMETER_NAME")
    private String parameterName;

    @Column(name = "PARAMETER_TYPE")
    private String parameterType;
}