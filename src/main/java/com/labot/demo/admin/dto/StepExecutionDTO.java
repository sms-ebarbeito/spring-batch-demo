package com.labot.demo.admin.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class StepExecutionDTO {
    private String stepName;
    private String status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Long readCount;
    private Long writeCount;
    private Long commitCount;
    private Long skipCount;
    private Long rollbackCount;
    private Long readSkipCount;
    private Long processSkipCount;
    private Long writeSkipCount;

}
