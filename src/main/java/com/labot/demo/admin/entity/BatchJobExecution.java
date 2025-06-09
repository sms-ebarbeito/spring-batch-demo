package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
@Data
public class BatchJobExecution {
    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobId;

    @Column(name = "VERSION")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "JOB_INSTANCE_ID", nullable = false)
    private BatchJobInstance jobInstance;

    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "STATUS", length = 10)
    private String status;

    @Column(name = "EXIT_CODE", length = 2500)
    private String exitCode;

    @Column(name = "EXIT_MESSAGE", length = 2500)
    private String exitMessage;

    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;

    @OneToMany(mappedBy = "jobExecution")
    private List<BatchJobExecutionParam> params;

    @OneToMany(mappedBy = "jobExecution")
    private List<BatchStepExecution> steps;

    @OneToOne(mappedBy = "jobExecution")
    private BatchJobExecutionContext context;
}
