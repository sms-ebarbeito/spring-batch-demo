package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_CONTEXT")
@Data
public class BatchJobExecutionContext {
    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private BatchJobExecution jobExecution;

    @Column(name = "SHORT_CONTEXT", length = 2500)
    private String shortContext;

    @Column(name = "SERIALIZED_CONTEXT", columnDefinition = "TEXT")
    private String serializedContext;
}