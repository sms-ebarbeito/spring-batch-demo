package com.labot.demo.admin.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class JobExecutionDTO {

    private Long jobId;
    private String jobName;
    private String status;
    private Timestamp startTime;
    private Timestamp endTime;

    private List<StepExecutionDTO> stepExecutions;

    private Map<String, Object> jobParameters;

    private Long progress;
    private String unitProgress = "%";
    private Long totalToProcess;
    private Long position;

}
