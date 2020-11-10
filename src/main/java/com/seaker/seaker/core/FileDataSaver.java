package com.seaker.seaker.core;

public class FileDataSaver implements DataSaver {

    @Override
    public void init(String name) {

    }

    @Override
    public void release() {

    }

    @Override
    public void save(String data) {

    }

    @Override
    public void close() throws Exception {
        release();
    }
}
