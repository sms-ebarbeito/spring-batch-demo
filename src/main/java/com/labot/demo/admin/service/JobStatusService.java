package com.labot.demo.admin.service;

import com.labot.demo.admin.dto.JobExecutionDTO;
import com.labot.demo.admin.exception.JobExecutionNotFoundException;
import com.labot.demo.admin.exception.NotFoundEntityException;
import com.labot.demo.admin.mapper.BatchDtoMapper;
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
import java.util.stream.Collectors;

import static com.labot.demo.admin.utils.PageBatchUtils.*;


@Service
@Slf4j
public class JobStatusService {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    public JobStatusService(@Qualifier("batchJobExplorer") JobExplorer jobExplorer,
                            @Autowired JobOperator jobOperator) {
        this.jobExplorer = jobExplorer;
        this.jobOperator = jobOperator;
    }

    public JobExecution getJobStatus(Long jobExecutionId) {
        return jobExplorer.getJobExecution(jobExecutionId);
    }

    public JobExecutionDTO getJobExecutionById(Long jobExecutionId) {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
        if (jobExecution == null) {
            throw new JobExecutionNotFoundException("JOB OPERATOR ERROR", "JobExecution with ID " + jobExecutionId + " not found");
        }
        return BatchDtoMapper.convertToDTO(jobExecution);
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

        List<JobExecutionDTO> jobExecutionDTOs = jobExplorer.getJobNames().stream()
                .flatMap(jobName -> jobExplorer.findJobInstancesByJobName(jobName, 0, Integer.MAX_VALUE).stream())
                .map(instance -> jobExplorer.getJobExecutions(instance))
                .flatMap(List::stream)
                .filter(jobExecution -> filterJobExecution(jobExecution, search)) // Apply search filtering
                .sorted(getComparator(orderBy, direction)) // Apply sorting
                .map(BatchDtoMapper::convertToDTO)
                .collect(Collectors.toList());

        return createPage(jobExecutionDTOs, pageable);
    }

    /**
     * Returns the last job with the name 'jobName'
     * @param jobName the name of the last job we are looking for
     * @return JobExecutionDTO
     */
    public JobExecutionDTO getMostRecentJobExecution(String jobName) {
        List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(jobName, 0, Integer.MAX_VALUE);
        if (jobInstances.isEmpty()) {
            log.error("No job instances found for job name: {}", jobName);
            throw new NotFoundEntityException("JOB OPERATOR ERROR", "There is no job with name: " + jobName);
        }
        JobInstance lastJob = jobInstances.stream().max(Comparator.comparingLong(Entity::getId)).get();

        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(lastJob);

        if (jobExecutions.isEmpty()) {
            log.warn("No job executions found for job name: {}", jobName);
            throw new NotFoundEntityException("JOB OPERATOR ERROR", "There is no executions for the job with name: " + jobName);
        }

        JobExecution mostRecentJobExecution = jobExecutions.stream()
                .max(Comparator.comparing(JobExecution::getStartTime))
                .orElseThrow(() -> new JobExecutionNotFoundException("JOB OPERATOR ERROR", "No se encontró un Job para el nombre " + jobName));

        return BatchDtoMapper.convertToDTO(mostRecentJobExecution);
    }

}
