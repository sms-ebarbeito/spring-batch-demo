package com.labot.demo.controller;

import com.labot.demo.batch.service.BatchStarterService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class BatchController {

    private final BatchStarterService batchStarterService;

    public BatchController(BatchStarterService batchStarterService) {
        this.batchStarterService = batchStarterService;
    }

    @GetMapping("/batch/execute")
    public Long runBatchReports() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("EXECUTION_TIME", new Date())
                .toJobParameters();
        return batchStarterService.createImportTask(jobParameters);
    }
}
