package com.seaker.seaker.configuration;

import com.seaker.seaker.core.DataSaver;
import com.seaker.seaker.core.FileDataSaver;
import com.seaker.seaker.vk.parser.LeftsFromFriendsParser;
import com.seaker.seaker.vk.parser.LeftsInCityParser;
import com.seaker.seaker.core.SystemOutDataSaver;
import com.seaker.seaker.properties.VKProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean(name = "fileDataSaver")
    @ConditionalOnProperty(prefix = "data", value = "output", havingValue = "file", matchIfMissing = true)
    public DataSaver fileDataSaver() {
        return new FileDataSaver();
    }

    @Bean(name = "consoleDataSaver")
    @ConditionalOnProperty(prefix = "data", value = "output", havingValue = "console", matchIfMissing = false)
    public DataSaver consoleDataSaver() {
        return new FileDataSaver();
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
