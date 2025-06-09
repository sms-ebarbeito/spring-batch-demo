package com.labot.demo.admin.service;

import com.labot.demo.admin.dto.JobExecutionDTO;
import com.labot.demo.admin.entity.BatchJobExecution;
import com.labot.demo.admin.exception.JobExecutionNotFoundException;
import com.labot.demo.admin.exception.NotFoundEntityException;
import com.labot.demo.admin.mapper.BatchDtoMapper;
import com.labot.demo.admin.repository.jpa.BatchJobExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Entity;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class JobStatusService {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final BatchJobExecutionRepository jobRepository;

    public JobStatusService(@Qualifier("batchJobExplorer") JobExplorer jobExplorer,
                            @Autowired JobOperator jobOperator,
                            @Autowired BatchJobExecutionRepository jobRepository) {
        this.jobExplorer = jobExplorer;
        this.jobOperator = jobOperator;
        this.jobRepository = jobRepository;
    }

    public JobExecution getJobStatus(Long jobExecutionId) {
        return jobExplorer.getJobExecution(jobExecutionId);
    }

    public JobExecutionDTO getJobExecutionById(Long jobExecutionId) {
        var jobExecution = jobRepository.findByJobId(jobExecutionId).orElseThrow(() ->
                new JobExecutionNotFoundException("JOB OPERATOR ERROR", "JobExecution with ID " + jobExecutionId + " not found")
        );
        return BatchDtoMapper.convertToDTO(jobExecution, this.getTotalItems(jobExecution));
    }

    public boolean stopJob(Long jobExecutionId) {
        try {
            if (isCompleted(jobExecutionId)) {
                return true;
            }
            log.info("Stopping job {}", jobExecutionId);
            jobOperator.stop(jobExecutionId);
            log.info("Job {} stopped", jobExecutionId);
            return true;
        } catch (Exception e) {
            log.error("ERROR: Trying to stop job {}", jobExecutionId);
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean isCompleted(Long jobExecutionId) {
        try {
            return jobOperator.getSummary(jobExecutionId).toUpperCase().contains("COMPLETED");
        } catch (NoSuchJobExecutionException e) {
            return false;
        }
    }

    public Page<JobExecutionDTO> listAllJobExecutions(int page, int size, String orderBy, String direction, String search) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BatchJobExecution> pageResult = jobRepository.search(search, pageable);

        return pageResult.map(item -> BatchDtoMapper.convertToDTO(
                item,
                this.getTotalItems(item)
            )
        );

    }

    /**
     * Returns the last job with the name 'jobName'
     * @param jobName the name of the last job we are looking for
     * @return JobExecutionDTO
     */
    public JobExecutionDTO getMostRecentJobExecution(String jobName) {
//        List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(jobName, 0, Integer.MAX_VALUE);
//        if (jobInstances.isEmpty()) {
//            log.error("No job instances found for job name: {}", jobName);
//            throw new NotFoundEntityException("JOB OPERATOR ERROR", "There is no job with name: " + jobName);
//        }
//        JobInstance lastJob = jobInstances.stream().max(Comparator.comparingLong(Entity::getId)).get();
//
//        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(lastJob);
//
//        if (jobExecutions.isEmpty()) {
//            log.warn("No job executions found for job name: {}", jobName);
//            throw new NotFoundEntityException("JOB OPERATOR ERROR", "There is no executions for the job with name: " + jobName);
//        }
//
//        JobExecution mostRecentJobExecution = jobExecutions.stream()
//                .max(Comparator.comparing(JobExecution::getStartTime))
//                .orElseThrow(() -> new JobExecutionNotFoundException("JOB OPERATOR ERROR", "No se encontró un Job para el nombre " + jobName));

        //return BatchDtoMapper.convertToDTO(mostRecentJobExecution);
        BatchJobExecution jobExecution = jobRepository.findLastByJobName(jobName).orElseThrow(() ->
            new NotFoundEntityException("JOB OPERATOR ERROR", "There is no job with name: " + jobName)
        );
        return BatchDtoMapper.convertToDTO(jobExecution, this.getTotalItems(jobExecution));
    }

    private Long getTotalItems(BatchJobExecution batchJobExecution) {
        return jobExplorer.getJobExecution(batchJobExecution.getJobId()).getExecutionContext().getLong("totalItems");
    }

}
