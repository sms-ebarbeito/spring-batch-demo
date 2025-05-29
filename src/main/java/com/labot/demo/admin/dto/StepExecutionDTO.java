package com.labot.demo.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StepExecutionDTO {
    private String stepName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long readCount;
    private Long writeCount;
    private Long commitCount;
    private Long skipCount;
    private Long rollbackCount;
    private Long readSkipCount;
    private Long processSkipCount;
    private Long writeSkipCount;

}
