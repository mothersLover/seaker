package com.seaker.seaker.core;

public interface DataSaver extends AutoCloseable {

    void init(String name);

    void release();

    void save(String data);
}
