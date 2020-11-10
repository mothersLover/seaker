package com.seaker.seaker.vk.parser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seaker.seaker.SeakerConstant;
import com.seaker.seaker.core.DataSaver;
import com.seaker.seaker.properties.VKProperties;
import com.seaker.seaker.vk.RetrieveAllLeftsFromCityVKRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.seaker.seaker.SeakerConstant.*;

public class LeftsInCityParser {
    private final VKProperties vkProperties;
    private final DataSaver dataSaver;

    public LeftsInCityParser(VKProperties vkProperties, DataSaver dataSaver) {
        this.vkProperties = vkProperties;
        this.dataSaver = dataSaver;
    }

    public void collectForCity(String cityCode, String cityName, int ageFrom, int ageTo) throws IOException {
        try {
            dataSaver.init(LocalDateTime.now() + "-" + cityName + "-коммунисты-и-социалисты-от-" + ageFrom + "-до-" + ageTo);
            RetrieveAllLeftsFromCityVKRequest.Builder request = RetrieveAllLeftsFromCityVKRequest.builder();
            request.setCity(cityCode)
                    .setCode(SeakerConstant.code)
                    .setTemplate(vkProperties.getTemplate())
                    .setToken(vkProperties.getToken());
            Map<String, Map<String, String>> cityResult = new HashMap<>();
            StringBuilder allResultForCity = new StringBuilder();
            scanForMen(ageFrom, ageTo, request, cityResult, allResultForCity);
            scanForWomen(ageFrom, ageTo, request, cityResult, allResultForCity);
            dataSaver.save(cityName + " : " + allResultForCity);
        } finally {
            dataSaver.release();
        }
    }

    private void scanForWomen(int ageFrom, int ageTo, RetrieveAllLeftsFromCityVKRequest.Builder request, Map<String, Map<String, String>> cityResult, StringBuilder allResultForCity) throws IOException {
        dataSaver.save("Женщины");
        cityResult.put(FEMALE_SEX_ID, new HashMap<>());
        request.setSex(FEMALE_SEX_ID);
        scanAge(ageFrom, ageTo, request, cityResult, allResultForCity, FEMALE_SEX_ID);
    }

    private void scanForMen(int ageFrom, int ageTo, RetrieveAllLeftsFromCityVKRequest.Builder request, Map<String, Map<String, String>> cityResult, StringBuilder allResultForCity) throws IOException {
        dataSaver.save("Мужчины");
        cityResult.put(MALE_SEX_ID, new HashMap<>());
        request.setSex(MALE_SEX_ID);
        scanAge(ageFrom, ageTo, request, cityResult, allResultForCity, MALE_SEX_ID);
    }

    private void scanAge(int ageFrom, int ageTo, RetrieveAllLeftsFromCityVKRequest.Builder request, Map<String, Map<String, String>> cityResult,
                         StringBuilder allResultForCity, String sexId) throws IOException {
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
                        RetrieveAllLeftsFromCityVKRequest build = request.build();
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
            dataSaver.save(ageBorders + " : " + ageResult);
        }
    }
}
