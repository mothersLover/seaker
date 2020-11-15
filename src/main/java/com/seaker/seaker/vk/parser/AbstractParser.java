package com.seaker.seaker.vk.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractParser {

    protected Set<Long> extractIdsFromString(String allResultForCity) {
        String replace = allResultForCity.replace(" ", "");
        String[] ids = replace.split(",");
        Set<Long> result = new HashSet<>();
        Arrays.stream(ids).forEach(id -> result.add(Long.parseLong(id)));
        return result;
    }
}
