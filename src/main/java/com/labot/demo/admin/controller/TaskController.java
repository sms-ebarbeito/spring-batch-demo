package com.labot.demo.admin.controller;

import com.labot.demo.admin.dto.JobExecutionDTO;
import com.labot.demo.admin.service.JobStatusService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private final JobStatusService jobStatusService;

    public TaskController(JobStatusService jobStatusService) {
        this.jobStatusService = jobStatusService;
    }

    @GetMapping("/jobs")
    public Page<JobExecutionDTO> listAllJobExecutions(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                      @RequestParam(value = "orderBy", defaultValue = "code") String orderBy,
                                                      @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                      @RequestParam(value = "search", required = false) String search) {
        return jobStatusService.listAllJobExecutions(page, size, orderBy, direction, search);
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<JobExecutionDTO> getJobExecutionById(@PathVariable("id") Long jobExecutionId) {
        JobExecutionDTO jobExecutionDTO = jobStatusService.getJobExecutionById(jobExecutionId);
        return ResponseEntity.ok(jobExecutionDTO);
    }

    @DeleteMapping("/job/{id}")
    public Map<String, Object> stopJob(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("task", id);
        result.put("stopped", jobStatusService.stopJob(id));
        return result;
    }

    /**
     * Get the last Job Execution for the Job name
     * @param name
     * @return
     */
    @GetMapping("/job/last/{name}")
    public ResponseEntity<JobExecutionDTO> getJobExecutionLastByName(@PathVariable("name") String name) {
        JobExecutionDTO jobExecutionDTO = jobStatusService.getMostRecentJobExecution(name);
        return ResponseEntity.ok(jobExecutionDTO);
    }


}