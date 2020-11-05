package com.seaker.seaker;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class SeakerConstant {
    public static final int DELAY_ONE_SECOND = 1000;
    public static final String FEMALE_SEX_ID = "1";
    public static final String MALE_SEX_ID = "2";
    public static String code = "var c = API.users.search({\n" +
            "    \"count\": \"333\",\n" +
            "    \"fields\": \"personal\",\n" +
            "    \"city\": %s,\n" +
            "    \"sex\": %s,\n" +
            "    \"age_from\": %s,\n" +
            "    \"age_to\": %s,\n" +
            "    \"birth_month\": %s,\n" +
            "    \"offset\": %s,\n" +
            "    \"v\": \"5.122\"\n" +
            "});\n" +
            "\n" +
            "var i = 0;\n" +
            "var res = \"\";\n" +
            "while (i < 333) {\n" +
            "    var item = c.items[i];\n" +
            "    if (item.personal.political) {\n" +
            "        var polit = item.personal.political;\n" +
            "        if (polit == 1 || polit == 2) {\n" +
            "            res = res + item.id + \", \";\n" +
            "        }\n" +
            "    }\n" +
            "    i = i + 1;\n" +
            "}\n" +
            "return res;";
    public static Map<String, String> cities = new HashMap<>();
    static {
        cities.put("2", "Saint Petersburg");
        cities.put("1", "Moscow");
        cities.put("10", "Volgograd");
        cities.put("37", "Vladivostok");
        cities.put("153", "Khabarovsk");
        cities.put("60", "Kazan");
        cities.put("61", "Kaliningrad");
        cities.put("72", "Krasnodar");
        cities.put("73", "Krasnoyarsk");
        cities.put("95", "Nizhny Novgorod");
        cities.put("99", "Novosibirsk");
        cities.put("104", "Omsk");
        cities.put("110", "Perm");
        cities.put("119", "Rostov-on-Don");
        cities.put("123", "Samara");
        cities.put("151", "Ufa");
        cities.put("158", "Chelyabinsk");
    }

}
