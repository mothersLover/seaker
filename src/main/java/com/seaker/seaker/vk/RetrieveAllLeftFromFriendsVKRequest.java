package com.seaker.seaker.vk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RetrieveAllLeftFromFriendsVKRequest {
    private final String request;
    private final String code;
    private final String template;
    private final String token;

    private RetrieveAllLeftFromFriendsVKRequest(String template, String code, String token) {
        this.template = Objects.requireNonNull(template);
        this.code = Objects.requireNonNull(code);
        this.token = Objects.requireNonNull(token);
        this.request = generateRequest();
    }

    private String generateRequest() {
        return String.format(template, code, token);
    }

    public static RetrieveAllLeftFromFriendsVKRequest.Builder builder() {
        return new RetrieveAllLeftFromFriendsVKRequest.Builder();
    }

    public String getRequest() {
        return request;
    }

    public static class Builder {
        private String code;
        private String template;
        private String token;
        private String offset;
        private String city;
        private String userId;

        public RetrieveAllLeftFromFriendsVKRequest.Builder setOffset(String offset) {
            this.offset = offset;
            return this;
        }

        public RetrieveAllLeftFromFriendsVKRequest.Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public RetrieveAllLeftFromFriendsVKRequest.Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public RetrieveAllLeftFromFriendsVKRequest.Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public RetrieveAllLeftFromFriendsVKRequest.Builder setTemplate(String template) {
            this.template = template;
            return this;
        }

        public RetrieveAllLeftFromFriendsVKRequest.Builder setToken(String token) {
            this.token = token;
            return this;
        }

        private String generateCode() throws UnsupportedEncodingException {
            return URLEncoder.encode(String.format(code, userId, offset, city),
                    StandardCharsets.UTF_8.toString());
        }

        public RetrieveAllLeftFromFriendsVKRequest build() throws UnsupportedEncodingException {
            return new RetrieveAllLeftFromFriendsVKRequest(template, generateCode(), token);
        }
    }
}
