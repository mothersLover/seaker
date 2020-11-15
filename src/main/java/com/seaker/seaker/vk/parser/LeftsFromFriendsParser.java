package com.seaker.seaker.vk.parser;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.seaker.seaker.SeakerConstant;
import com.seaker.seaker.core.DataSaver;
import com.seaker.seaker.properties.VKProperties;
import com.seaker.seaker.vk.CitiesEnum;
import com.seaker.seaker.vk.RetrieveAllLeftFromFriendsVKRequest;
import com.seaker.seaker.vk.RetrieveAllLeftsFromCityVKRequest;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.seaker.seaker.SeakerConstant.DELAY_ONE_SECOND;

public class LeftsFromFriendsParser extends AbstractParser {
    private final DataSaver dataSaver;
    private final VKProperties vkProperties;
    Set<Long> existingLefts = new HashSet<>();

    public LeftsFromFriendsParser(DataSaver dataSaver, VKProperties vkProperties) {
        this.dataSaver = dataSaver;
        this.vkProperties = vkProperties;
    }

    public Set<Long> collectAllLeftsFromGivenLeftIds(CitiesEnum city, String ids) throws IOException, InterruptedException {
        try {
            existingLefts = extractIdsFromString(ids);
            dataSaver.init(LocalDateTime.now() + "-" + city.getName() + "-выборка-через-друзей-");
            Set<Long> newLefts = new HashSet<>();
            RetrieveAllLeftFromFriendsVKRequest.Builder builder = RetrieveAllLeftFromFriendsVKRequest.builder();
            builder.setCity(city.getCode()).
                    setTemplate(vkProperties.getTemplate()).
                    setToken(vkProperties.getToken()).
                    setCode(SeakerConstant.LEFT_IN_FRIENDS_REQUEST_CODE);
            Object[] array = existingLefts.toArray();
            int iter = 0;
            int token = 0;
            for (Object o : array) {
                String id = String.valueOf(o);
                if (iter >= 300) {
                    token++;
                    if (token >= vkProperties.getTokens().size()) {
                        token = 0;
                        dataSaver.save("The last id - " + id);
                        dataSaver.save("Intermediate result : " + newLefts + " of new lefts - " + newLefts.toString());
                        Thread.sleep(DELAY_ONE_SECOND * 60 * 60 * 24);
                    }
                    builder.setToken(vkProperties.getTokens().get(token));
                    iter = 0;
                }
                iter += executeRequestForId(id, newLefts, builder);
                builder.setToken(vkProperties.getTokens().get(token));
            }
            dataSaver.save(city.getName() + " : " + newLefts.size() + " : " + newLefts.toString());
            return newLefts;
        } finally {
            dataSaver.release();
        }
    }

    private int executeRequestForId(String userId, Set<Long> newLefts,
                                  RetrieveAllLeftFromFriendsVKRequest.Builder builder) throws UnsupportedEncodingException, MalformedURLException {
        builder.setUserId(userId);
        int i = 0;
        for (int offset = 0; offset < 700; offset += 333) {
            builder.setOffset(String.valueOf(offset));
            RetrieveAllLeftFromFriendsVKRequest vkRequest = builder.build();
            URL url = new URL(vkRequest.getRequest());
            try(InputStream inputStream = url.openStream()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
                    JsonElement response = jsonObject.get("response");
                    String responseString = response.toString();
                    String normalizedResponse = responseString.replace("\"", "");
                    String idsString = normalizedResponse.replace(" ", "");
                    String[] ids = idsString.split(",");
                    for (String stringId : ids) {
                        if (Strings.isNullOrEmpty(stringId)) {
                            continue;
                        }
                        Long id = Long.valueOf(stringId);
                        if (existingLefts.contains(id) || newLefts.contains(id)) {
                            continue;
                        }
                        newLefts.add(id);
                    }
                    i++;
                    if (ids.length < 333) {
                        Thread.sleep(DELAY_ONE_SECOND);
                        return i;
                    }
                }
                Thread.sleep(DELAY_ONE_SECOND);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return i;
    }
}
