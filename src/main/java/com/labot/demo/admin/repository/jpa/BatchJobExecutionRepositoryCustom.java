package com.labot.demo.admin.repository.jpa;

import com.labot.demo.admin.entity.BatchJobExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BatchJobExecutionRepositoryCustom {
    Page<BatchJobExecution> search(String search, Pageable pageable);
}
