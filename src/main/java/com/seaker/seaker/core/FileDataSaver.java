package com.seaker.seaker.core;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataSaver implements DataSaver {
    FileOutputStream fileOutputStream;

    @Override
    public void init(String name) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(name + ".txt")) {
            this.fileOutputStream = fileOutputStream;
        }
    }

    @Override
    public void release() throws IOException {
        fileOutputStream.close();
    }

    @Override
    public void save(String data) throws IOException {
        fileOutputStream.write(data.getBytes());
    }

    @Override
    public void close() throws Exception {
        release();
    }
}
