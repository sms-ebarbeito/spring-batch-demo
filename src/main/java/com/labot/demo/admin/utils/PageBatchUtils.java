package com.labot.demo.admin.utils;

import com.labot.demo.admin.dto.JobExecutionDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageBatchUtils {

    public static boolean filterJobExecution(JobExecution jobExecution, String search) {
        if (search == null || search.isEmpty()) {
            return true;
        }

        Map<String, String> filters = parseSearchParams(search);

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue().toLowerCase();

            switch (key) {
                case "status":
                    if (!jobExecution.getStatus().toString().toLowerCase().contains(value)) {
                        return false;
                    }
                    break;
                case "jobname":
                    if (!jobExecution.getJobInstance().getJobName().toLowerCase().contains(value)) {
                        return false;
                    }
                    break;
                case "starttime":
                    if (jobExecution.getStartTime() != null &&!jobExecution.getStartTime().toString().toLowerCase().contains(value)) {
                        return false;
                    }
                    break;
                case "endtime":
                    if (jobExecution.getEndTime() != null && !jobExecution.getEndTime().toString().toLowerCase().contains(value)) {
                        return false;
                    }
                    break;
                case "jobid":
                    if (!jobExecution.getId().toString().equals(value)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }

        return true;
    }


    public static Comparator<JobExecution> getComparator(String orderBy, String direction) {
        Comparator<JobExecution> comparator;

        switch (orderBy) {
            case "jobName":
                comparator = Comparator.comparing(jobExecution -> jobExecution.getJobInstance().getJobName().toLowerCase());
                break;
            case "status":
                comparator = Comparator.comparing(jobExecution -> jobExecution.getStatus().toString().toLowerCase());
                break;
            case "startTime":
                comparator = Comparator.comparing(JobExecution::getStartTime);
                break;
            case "endTime":
                comparator = Comparator.comparing(JobExecution::getEndTime);
                break;
            default:
                comparator = Comparator.comparing(JobExecution::getId);
                break;
        }

        if (direction.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public static Page<JobExecutionDTO> createPage(List<JobExecutionDTO> jobExecutionDTOs, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), jobExecutionDTOs.size());

        List<JobExecutionDTO> paginatedList = jobExecutionDTOs.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, jobExecutionDTOs.size());
    }

    public static Map<String, String> parseSearchParams(String search) {
        return Arrays.stream(search.split(","))
                .map(param -> param.split("="))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0].trim().toLowerCase(), parts -> parts[1].trim().toLowerCase()));
    }
}
