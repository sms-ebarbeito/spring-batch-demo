package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BATCH_STEP_EXECUTION_CONTEXT")
@Data
public class BatchStepExecutionContext {
    @Id
    @Column(name = "STEP_EXECUTION_ID")
    private Long stepExecutionId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "STEP_EXECUTION_ID")
    private BatchStepExecution stepExecution;

    @Column(name = "SHORT_CONTEXT", length = 2500)
    private String shortContext;

    @Column(name = "SERIALIZED_CONTEXT", columnDefinition = "TEXT")
    private String serializedContext;
}