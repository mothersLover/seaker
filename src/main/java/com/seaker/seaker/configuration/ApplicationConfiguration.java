package com.seaker.seaker.configuration;

import com.seaker.seaker.Parser;
import com.seaker.seaker.properties.VKProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Parser parser(VKProperties vkProperties) {
        return new Parser(vkProperties);
    }
}
