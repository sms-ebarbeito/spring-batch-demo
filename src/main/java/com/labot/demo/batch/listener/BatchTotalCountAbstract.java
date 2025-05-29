package com.labot.demo.batch.listener;

import lombok.Setter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@JobScope
public class BatchTotalCountAbstract implements JobExecutionListener {

    protected final JobRepository jobRepository;
    @Setter
    protected DelegatedTotalCountReaderRepository delegatedTotalCountReaderRepository;


    public BatchTotalCountAbstract(@Qualifier("batchJobRepository") JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        long totalItems = delegatedTotalCountReaderRepository.getTotalCount();
        jobExecution.getExecutionContext().putLong("totalItems", totalItems);
        jobRepository.updateExecutionContext(jobExecution);
    }


}
