package com.labot.demo.admin.mapper;

import com.labot.demo.admin.dto.JobExecutionDTO;
import com.labot.demo.admin.dto.StepExecutionDTO;
import com.labot.demo.admin.entity.BatchJobExecution;
import com.labot.demo.admin.entity.BatchJobExecutionParam;
import com.labot.demo.admin.entity.BatchStepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BatchDtoMapper {

    public static JobExecutionDTO convertToDTO(BatchJobExecution jobExecution, Long totalItems) {
        JobExecutionDTO dto = new JobExecutionDTO();
        dto.setJobId(jobExecution.getJobId());
        dto.setJobName(jobExecution.getJobInstance().getJobName());
        dto.setStatus(jobExecution.getStatus());
        dto.setStartTime(jobExecution.getStartTime() != null ? jobExecution.getStartTime() : null);
        dto.setEndTime(jobExecution.getEndTime() != null ? jobExecution.getEndTime() : null);
        dto.setStepExecutions(convertStepExecutions(jobExecution.getSteps()));
        dto.setJobParameters(convertJobParameters(jobExecution.getParams()));

        //long totalItems = jobExecution.getContext().getLong("totalItems", 0);
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

    private static List<StepExecutionDTO> convertStepExecutions(Collection<BatchStepExecution> stepExecutions) {
        return stepExecutions.stream().map(step -> {
            StepExecutionDTO stepDto = new StepExecutionDTO();
            stepDto.setStepName(step.getStepName());
            stepDto.setStatus(step.getStatus().toString());
            stepDto.setStartTime(step.getStartTime() != null ? step.getStartTime() : null);
            stepDto.setEndTime(step.getEndTime() != null ? step.getEndTime() : null);
            stepDto.setReadCount(step.getReadCount());
            stepDto.setWriteCount(step.getWriteCount());
            stepDto.setCommitCount(step.getCommitCount());
            stepDto.setSkipCount(step.getReadSkipCount());
            stepDto.setRollbackCount(step.getRollbackCount());
            stepDto.setReadSkipCount(step.getReadSkipCount());
            stepDto.setProcessSkipCount(step.getProcessSkipCount());
            stepDto.setWriteSkipCount(step.getWriteSkipCount());
            return stepDto;
        }).collect(Collectors.toList());
    }

//    private static Map<String, Object> convertJobParameters(Map<String, JobParameter<?>> jobParameters) {
//        return jobParameters.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        entry -> entry.getValue().getValue()
//                ));
//    }

    private static Map<String, Object> convertJobParameters(List<BatchJobExecutionParam> jobParameters) {
        var map = new HashMap<String, Object>();
        for (BatchJobExecutionParam param : jobParameters) {
            map.put(param.getIdentifying(), param.getParameterValue());
        }
        return map;
    }

}
