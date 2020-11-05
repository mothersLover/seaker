package com.seaker.seaker.vk;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CodeReader {

    public String readCode () {
        URL resource = getClass().getClassLoader().getResource("left-in-city.js");
        String result = "";
        try {
            assert resource != null;
            List<String> strings = Files.readAllLines(Paths.get(resource.toURI()));
            result = String.join("", strings);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        if (Strings.isNullOrEmpty(result)) {
            throw new IllegalArgumentException();
        }
        return result;
    }
}
