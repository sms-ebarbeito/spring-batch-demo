package com.labot.demo.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

@Slf4j
public class ServiceItemWriterAbstract<T> implements ItemWriter<T>, InitializingBean {

    protected static final Log logger = LogFactory.getLog(ServiceItemWriterAbstract.class);
    private DelegatedItemWriterService<T> service;
    private String stepName = "Undefined";
    private boolean bulkSave = false;

    public ServiceItemWriterAbstract() {
    }

    public void setService(DelegatedItemWriterService service) {
        this.service = service;
    }

    public void setStepName(String stepName){
        this.stepName = stepName;
    }

    public void setBulkSave(boolean bulkSave){
        this.bulkSave = bulkSave;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.state(this.service != null, "A DelegatedItemWriterService implementation is required");
    }

    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("Writing to the service with " + chunk.getItems().size() + " items.");
        }

        if(!bulkSave) {
            for (T item : chunk.getItems()) {
                service.write(item);
            }
        } else {
            log.info("[Step:"+ stepName + "] Bulk Save.");
            service.write(chunk.getItems());
        }

        service.flushItems();

        log.info("[Step:"+ stepName + "] Write is commit.");
    }
}
