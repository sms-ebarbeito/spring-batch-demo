package com.labot.demo.batch.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchStarterService {
    private final JobLauncher jobLauncher;
    private final Job jobImports;

    public BatchStarterService(@Qualifier("jobBatchLauncher") JobLauncher jobLauncher,
                               @Qualifier("jobImport") Job jobImports) {
        this.jobLauncher = jobLauncher;
        this.jobImports =  jobImports;
    }

    public Long createImportTask(JobParameters jobParameters)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        JobExecution jobExecution = jobLauncher.run(jobImports, jobParameters);
        return jobExecution.getId();
    }
}
