package com.seaker.seaker.configuration;

import com.seaker.seaker.core.DataSaver;
import com.seaker.seaker.vk.parser.LeftsFromFriendsParser;
import com.seaker.seaker.vk.parser.LeftsInCityParser;
import com.seaker.seaker.core.SystemOutDataSaver;
import com.seaker.seaker.properties.VKProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public DataSaver dataSaver() {
        return new SystemOutDataSaver();
    }

    @Bean
    public LeftsInCityParser cityParser(VKProperties vkProperties, DataSaver dataSaver) {
        return new LeftsInCityParser(vkProperties, dataSaver);
    }

    @Bean
    public LeftsFromFriendsParser friendsParser(VKProperties vkProperties, DataSaver dataSaver) {
        return new LeftsFromFriendsParser(dataSaver, vkProperties);
    }
}
