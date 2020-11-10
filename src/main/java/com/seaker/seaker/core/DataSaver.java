package com.seaker.seaker.core;

import java.io.IOException;

public interface DataSaver extends AutoCloseable {

    void init(String name) throws IOException;

    void release() throws IOException;

    void save(String data) throws IOException;
}
