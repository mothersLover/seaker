package com.seaker.seaker.core;

public class SystemOutDataSaver implements DataSaver {

    @Override
    public void init(String name) {
        System.out.println(name);
    }

    @Override
    public void release() {
        //not implemented
    }

    @Override
    public void save(String data) {
        System.out.println(data);
    }

    @Override
    public void close() throws Exception {
        release();
    }
}
