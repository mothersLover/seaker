package com.seaker.seaker.vk;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class VKRequestBuilder {
    private final String request;
    private final String code;
    private final String template;
    private final String token;

    private VKRequestBuilder(String template, String code, String token) {
        this.template = Objects.requireNonNull(template);
        this.code = Objects.requireNonNull(code);
        this.token = Objects.requireNonNull(token);
        this.request = generateRequest();
    }

    private String generateRequest() {
        return String.format(template, code, token);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getRequest() {
        return request;
    }

    public static class Builder {
        private String code;
        private String template;
        private String token;
        private String sex;
        private String ageFrom;
        private String ageTo;
        private String month;
        private String offset;
        private String city;

        public Builder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setMonth(String month) {
            this.month = month;
            return this;
        }

        public Builder setAgeFrom(String ageFrom) {
            this.ageFrom = ageFrom;
            return this;
        }

        public Builder setAgeTo(String ageTo) {
            this.ageTo = ageTo;
            return this;
        }

        public Builder setOffset(String offset) {
            this.offset = offset;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setTemplate(String template) {
            this.template = template;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        private String generateCode() throws UnsupportedEncodingException {
            return URLEncoder.encode(String.format(code, city, sex, ageFrom, ageTo, month, offset),
                    StandardCharsets.UTF_8.toString());
        }

        public VKRequestBuilder build() throws UnsupportedEncodingException {
            return new VKRequestBuilder(template, generateCode(), token);
        }
    }
}
