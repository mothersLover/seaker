package com.seaker.seaker;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seaker.seaker.properties.VKProperties;
import com.seaker.seaker.vk.VKRequestBuilder;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.seaker.seaker.SeakerConstant.*;

public class Parser {
    private final VKProperties vkProperties;

    public Parser(VKProperties vkProperties) {
        this.vkProperties = vkProperties;
    }

    public void collectForCity(String cityCode, String cityName, int ageFrom, int ageTo) throws UnsupportedEncodingException {
        System.out.println("##################################################################################");
        System.out.println(cityName + " коммунисты и социалисты от " + ageFrom + " до " + ageTo);
        String code = SeakerConstant.code;
        VKRequestBuilder.Builder request = VKRequestBuilder.builder();
        request.setCity(cityCode)
                .setCode(code)
                .setTemplate(vkProperties.getTemplate())
                .setToken(vkProperties.getToken());
        Map<String, Map<String, String>> cityResult = new HashMap<>();
        StringBuilder allResultForCity = new StringBuilder();
        scanForMen(ageFrom, ageTo, request, cityResult, allResultForCity);
        scanForWomen(ageFrom, ageTo, request, cityResult, allResultForCity);
        System.out.println(cityName + " : " + allResultForCity);
    }

    private void scanForWomen(int ageFrom, int ageTo, VKRequestBuilder.Builder request, Map<String, Map<String, String>> cityResult, StringBuilder allResultForCity) throws UnsupportedEncodingException {
        System.out.println("Женщины");
        cityResult.put(FEMALE_SEX_ID, new HashMap<>());
        request.setSex(FEMALE_SEX_ID);
        scanAge(ageFrom, ageTo, request, cityResult, allResultForCity, FEMALE_SEX_ID);
    }

    private void scanForMen(int ageFrom, int ageTo, VKRequestBuilder.Builder request, Map<String, Map<String, String>> cityResult, StringBuilder allResultForCity) throws UnsupportedEncodingException {
        System.out.println("Мужчины");
        cityResult.put(MALE_SEX_ID, new HashMap<>());
        request.setSex(MALE_SEX_ID);
        scanAge(ageFrom, ageTo, request, cityResult, allResultForCity, MALE_SEX_ID);
    }

    private void scanAge(int ageFrom, int ageTo, VKRequestBuilder.Builder request, Map<String, Map<String, String>> cityResult,
                         StringBuilder allResultForCity, String sexId) throws UnsupportedEncodingException {
        int iter = 0;
        int token = 1;
        for (int age = ageFrom; age <= ageTo; age++) {
            StringBuilder resultForAge = new StringBuilder();
            request.setAgeFrom(String.valueOf(age));
            request.setAgeTo(String.valueOf(age));
            for (int month = 1; month <= 12; month++) {
                request.setMonth(String.valueOf(month));
                for (int step = 0; step < 700; step += 333) {
                    request.setOffset(String.valueOf(step));
                    try {
                        if (iter >= 300) {
                            if (token > vkProperties.getTokens().size()) {
                                token = 0;
                                Thread.sleep(DELAY_ONE_SECOND * 60 * 60 * 24);
                            }
                            request.setToken(vkProperties.getTokens().get(token));
                            iter = 0;
                            token++;
                        }
                        VKRequestBuilder build = request.build();
                        String buildRequest = build.getRequest();
                        URL url = new URL(buildRequest);
                        InputStream inputStream = url.openStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
                            JsonElement responseForAge = jsonObject.get("response");
                            String rawResponseForAge = responseForAge.toString();
                            String normalizedResponseForAge = rawResponseForAge.replace("\"", "");
                            allResultForCity.append(normalizedResponseForAge);
                            resultForAge.append(normalizedResponseForAge);
                        }
                        iter++;
                        Thread.sleep(DELAY_ONE_SECOND);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            }
            String ageResult = resultForAge.toString();
            Map<String, String> ageRes = cityResult.get(sexId);
            String ageBorders = age + " - ";
            ageRes.put(ageBorders, ageResult);
            System.out.println(ageBorders + " : " + ageResult);
        }
    }
}
