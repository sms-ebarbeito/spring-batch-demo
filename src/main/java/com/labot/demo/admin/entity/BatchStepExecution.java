package com.labot.demo.admin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "BATCH_STEP_EXECUTION")
@Data
public class BatchStepExecution {
    @Id
    @Column(name = "STEP_EXECUTION_ID")
    private Long id;

    @Column(name = "VERSION")
    private Long version;

    @Column(name = "STEP_NAME")
    private String stepName;

    @ManyToOne
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private BatchJobExecution jobExecution;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMMIT_COUNT")
    private Long commitCount;

    @Column(name = "READ_COUNT")
    private Long readCount;

    @Column(name = "FILTER_COUNT")
    private Long filterCount;

    @Column(name = "WRITE_COUNT")
    private Long writeCount;

    @Column(name = "READ_SKIP_COUNT")
    private Long readSkipCount;

    @Column(name = "WRITE_SKIP_COUNT")
    private Long writeSkipCount;

    @Column(name = "PROCESS_SKIP_COUNT")
    private Long processSkipCount;

    @Column(name = "ROLLBACK_COUNT")
    private Long rollbackCount;

    @Column(name = "EXIT_CODE", length = 2500)
    private String exitCode;

    @Column(name = "EXIT_MESSAGE", length = 2500)
    private String exitMessage;

    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;

    @OneToOne(mappedBy = "stepExecution")
    private BatchStepExecutionContext context;
}