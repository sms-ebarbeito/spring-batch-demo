package com.labot.demo.admin.repository.jpa;

import com.labot.demo.admin.entity.BatchJobExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BatchJobExecutionRepository extends JpaRepository<BatchJobExecution, Long>, BatchJobExecutionRepositoryCustom {

    Optional<BatchJobExecution> findByJobId(Long jobId);

    @Query("""
        SELECT bje
        FROM BatchJobExecution bje 
        WHERE bje.jobInstance.jobName = :jobName
        ORDER BY bje.jobId DESC
        LIMIT 1
    """)
    Optional<BatchJobExecution> findLastByJobName(@Param("jobName") String jobName);
}
