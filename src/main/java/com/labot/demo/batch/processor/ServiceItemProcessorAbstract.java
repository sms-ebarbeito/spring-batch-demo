package com.labot.demo.batch.processor;

import org.springframework.batch.item.ItemProcessor;

public class ServiceItemProcessorAbstract implements ItemProcessor {

    private final DelegatedItemProcessorService processor;

    public ServiceItemProcessorAbstract (DelegatedItemProcessorService processor) {
        this.processor = processor;
    }

    @Override
    public Object process(Object in) throws Exception {
        return processor.process(in);
    }

}
