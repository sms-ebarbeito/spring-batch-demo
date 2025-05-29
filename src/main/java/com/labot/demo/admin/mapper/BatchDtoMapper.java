package com.labot.demo.admin.mapper;

import com.labot.demo.admin.dto.JobExecutionDTO;
import com.labot.demo.admin.dto.StepExecutionDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BatchDtoMapper {

    public static JobExecutionDTO convertToDTO(JobExecution jobExecution) {
        JobExecutionDTO dto = new JobExecutionDTO();
        dto.setJobId(jobExecution.getId());
        dto.setJobName(jobExecution.getJobInstance().getJobName());
        dto.setStatus(jobExecution.getStatus().toString());
        dto.setStartTime(jobExecution.getStartTime() != null ? jobExecution.getStartTime() : null);
        dto.setEndTime(jobExecution.getEndTime() != null ? jobExecution.getEndTime() : null);
        dto.setStepExecutions(convertStepExecutions(jobExecution.getStepExecutions()));
        dto.setJobParameters(convertJobParameters(jobExecution.getJobParameters().getParameters()));

        long totalItems = jobExecution.getExecutionContext().getLong("totalItems", 0);
        long processedItems = dto.getStepExecutions().stream().mapToLong(StepExecutionDTO::getWriteCount).sum();
        if ("COMPLETED".equals(dto.getStatus())) {
            dto.setProgress(100L);
        } else {
            long progress = (totalItems > 0) ? Math.round(((double) processedItems / totalItems) * 100) : 0;
            dto.setProgress(progress);
        }
        dto.setTotalToProcess(totalItems);
        dto.setPosition(processedItems);

        return dto;
    }

    private static List<StepExecutionDTO> convertStepExecutions(Collection<StepExecution> stepExecutions) {
        return stepExecutions.stream().map(step -> {
            StepExecutionDTO stepDto = new StepExecutionDTO();
            stepDto.setStepName(step.getStepName());
            stepDto.setStatus(step.getStatus().toString());
            stepDto.setStartTime(step.getStartTime() != null ? step.getStartTime() : null);
            stepDto.setEndTime(step.getEndTime() != null ? step.getEndTime() : null);
            stepDto.setReadCount(step.getReadCount());
            stepDto.setWriteCount(step.getWriteCount());
            stepDto.setCommitCount(step.getCommitCount());
            stepDto.setSkipCount(step.getSkipCount());
            stepDto.setRollbackCount(step.getRollbackCount());
            stepDto.setReadSkipCount(step.getReadSkipCount());
            stepDto.setProcessSkipCount(step.getProcessSkipCount());
            stepDto.setWriteSkipCount(step.getWriteSkipCount());
            return stepDto;
        }).collect(Collectors.toList());
    }

    private static Map<String, Object> convertJobParameters(Map<String, JobParameter<?>> jobParameters) {
        return jobParameters.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getValue()
                ));
    }
}
