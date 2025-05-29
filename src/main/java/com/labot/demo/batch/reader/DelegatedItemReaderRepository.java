package com.labot.demo.batch.reader;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DelegatedItemReaderRepository<T, F> {

    Page<T> readPage(F filter, Pageable pageable) throws InterruptedException;
}
