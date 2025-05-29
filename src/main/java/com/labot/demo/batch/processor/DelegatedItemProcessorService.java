package com.labot.demo.batch.processor;

public interface DelegatedItemProcessorService<T, F> {

    F process(T in) throws Exception;

}
