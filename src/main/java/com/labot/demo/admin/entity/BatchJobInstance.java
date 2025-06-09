package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
@Data
public class BatchJobInstance {
    @Id
    @Column(name = "JOB_INSTANCE_ID")
    private Long id;

    @Column(name = "VERSION")
    private Long version;

    @Column(name = "JOB_NAME", nullable = false)
    private String jobName;

    @Column(name = "JOB_KEY", nullable = false, length = 32)
    private String jobKey;

    @OneToMany(mappedBy = "jobInstance")
    private List<BatchJobExecution> executions;
}