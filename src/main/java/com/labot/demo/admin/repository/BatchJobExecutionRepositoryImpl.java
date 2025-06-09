package com.labot.demo.admin.repository;

import com.labot.demo.admin.entity.BatchJobExecution;
import com.labot.demo.admin.repository.jpa.BatchJobExecutionRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BatchJobExecutionRepositoryImpl implements BatchJobExecutionRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<BatchJobExecution> search(String search, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BatchJobExecution> cq = cb.createQuery(BatchJobExecution.class);
        Root<BatchJobExecution> root = cq.from(BatchJobExecution.class);
        root.fetch("jobInstance", JoinType.LEFT); // needed for jobName filtering

        List<Predicate> predicates = new ArrayList<>();
        Map<String, String> filters = parseSearchParams(search);

        filters.forEach((key, value) -> {
            switch (key) {
                case "status":
                    predicates.add(cb.like(cb.lower(root.get("status")), "%" + value.toLowerCase() + "%"));
                    break;
                case "jobname":
                    predicates.add(cb.like(cb.lower(root.get("jobInstance").get("jobName")), "%" + value.toLowerCase() + "%"));
                    break;
                case "starttime":
                    predicates.add(cb.like(cb.lower(cb.function("TO_CHAR", String.class, root.get("startTime"), cb.literal("YYYY-MM-DD HH24:MI:SS"))), "%" + value + "%"));
                    break;
                case "endtime":
                    predicates.add(cb.like(cb.lower(cb.function("TO_CHAR", String.class, root.get("endTime"), cb.literal("YYYY-MM-DD HH24:MI:SS"))), "%" + value + "%"));
                    break;
                case "jobid":
                    predicates.add(cb.equal(root.get("id"), Long.valueOf(value)));
                    break;
            }
        });

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));

        TypedQuery<BatchJobExecution> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<BatchJobExecution> countRoot = countQuery.from(BatchJobExecution.class);
        countRoot.join("jobInstance", JoinType.LEFT);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    private Map<String, String> parseSearchParams(String search) {
        return Arrays.stream(search.split(","))
                .map(param -> param.split("="))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0].trim().toLowerCase(), parts -> parts[1].trim().toLowerCase()));
    }
}
