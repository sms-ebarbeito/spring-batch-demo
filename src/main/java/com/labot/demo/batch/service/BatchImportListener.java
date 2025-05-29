package com.labot.demo.batch.service;

import com.labot.demo.batch.listener.BatchTotalCountAbstract;
import com.labot.demo.batch.listener.DelegatedTotalCountReaderRepository;
import com.labot.demo.repository.jpa.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@JobScope
@Slf4j
public class BatchImportListener extends BatchTotalCountAbstract implements DelegatedTotalCountReaderRepository {

    private final Map<String, String> jobParametersMap;
    private JobExecution jobExecution;

    private final ImportRepository importacionRepository;

    public BatchImportListener(@Qualifier("batchJobRepository") JobRepository jobRepository,
                               @Value("#{jobParameters}") Map<String, String> jobParametersMap,
                               @Autowired ImportRepository importacionRepository) {
        super(jobRepository);
        this.jobParametersMap = new HashMap<>(jobParametersMap);
        this.importacionRepository = importacionRepository;
        super.setDelegatedTotalCountReaderRepository(this);
    }

    @Override
    public long getTotalCount() {
        log.info("Spring batch, job started, Getting count rows");
        //if you need parameters from job you could get from jobParametersMap
        // String value = jobParametersMap.get("key_name")
        Long totalRecords = importacionRepository.count();
        log.info("Spring batch, Found {} rows", totalRecords);
        return totalRecords != null ? totalRecords : 0L;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        this.jobExecution = jobExecution;
        //You could add to execution context some info like counters, messages or whatever you need
        jobExecution.getExecutionContext().put("processed", 0);
        jobExecution.getExecutionContext().put("warning", 0);
        jobExecution.getExecutionContext().put("error", 0);

        super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
        ExecutionContext context = jobExecution.getExecutionContext();
        //when job is finished you could retrieve info from execution context
        int processed = context.getInt("processed");
        int warning = context.getInt("warning");
        int error =  context.getInt("error");

        log.info("Spring batch, Job finished, processed: {}, warning: {}, error: {}", processed, warning, error);
    }
}
