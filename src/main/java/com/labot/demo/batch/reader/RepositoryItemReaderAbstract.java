package com.labot.demo.batch.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class RepositoryItemReaderAbstract<T, F> extends AbstractPagingItemReader<T> {

    private final DelegatedItemReaderRepository<T, F> repositoryItemReader;

    private final F filter;

    private Sort sort;

    private String stepName = "Undefined";

    public RepositoryItemReaderAbstract(DelegatedItemReaderRepository<T, F> repositoryItemReader, F filter) {
        Assert.notNull(repositoryItemReader, "DelegatedItemReaderRepository must not be null");
        this.repositoryItemReader = repositoryItemReader;
        if (filter == null) {
            log.warn("Filter is null");
        }
        this.filter = filter;
    }

    public RepositoryItemReaderAbstract(DelegatedItemReaderRepository<T, F> repositoryItemReader, F filter, int pageSize) {
        this(repositoryItemReader, filter);
        setPageSize(pageSize);
    }

    public RepositoryItemReaderAbstract(DelegatedItemReaderRepository<T, F> repositoryItemReader, F filter, int pageSize, String stepName) {
        this(repositoryItemReader, filter);
        this.stepName = stepName;
        setPageSize(pageSize);
    }


    public RepositoryItemReaderAbstract(DelegatedItemReaderRepository<T, F> repositoryItemReader, F filter, int pageSize, final Sort sort) {
        this(repositoryItemReader, filter, pageSize);
        this.sort = sort;
    }

    @Override
    protected void doReadPage() {
        log.info("[Step:"+ stepName + "] Start reading page.");

        Pageable pageable;
        if (sort == null) {
            pageable = PageRequest.of(getPage(), getPageSize());
        } else {
            pageable = PageRequest.of(getPage(), getPageSize(), sort);
        }

        log.info("[Step:"+ stepName + "] Page Size: "+ pageable.getPageSize() + " - Page number: "  + pageable.getPageNumber());

        Page<T> pageResults = null;
        try {
            pageResults = repositoryItemReader.readPage(filter, pageable);
        } catch (InterruptedException e) {
            //Lock Interrupt error when setting total count with Latch
            throw new RuntimeException(e);
        }

        log.info("[Step:"+ stepName + "] Read page ends.");

        if (results == null) {
            results = new CopyOnWriteArrayList<T>();
        } else {
            results.clear();
        }

        if (!pageResults.getContent().isEmpty()) {
            log.info("[Step:"+ stepName + "] Add all to process.");
            results.addAll(pageResults.getContent());
            log.info("[Step:"+ stepName + "] Add all ends.");
        } else {
            log.info("[Step:"+ stepName + "] No results to process.");
        }
    }

    @Override
    protected void jumpToItem(int itemIndex) {
        //do nothing
    }
}
