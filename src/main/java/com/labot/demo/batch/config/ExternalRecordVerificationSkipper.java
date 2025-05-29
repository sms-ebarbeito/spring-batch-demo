package com.labot.demo.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.DataAccessResourceFailureException;

@Slf4j
public class ExternalRecordVerificationSkipper implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        if(t instanceof DataAccessResourceFailureException){
            log.error("Database Error, process ended", t);
            return false;
        }
        log.warn("Could not save", t);
        return true;
    }
}
