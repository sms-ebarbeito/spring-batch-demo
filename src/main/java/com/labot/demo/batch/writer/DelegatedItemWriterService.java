package com.labot.demo.batch.writer;

import java.util.List;

public interface DelegatedItemWriterService<T> {

    void write(T in);

    void write(List<? extends T> inputList);

    void flushItems();
}
