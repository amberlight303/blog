package com.amberlight.blog.gateway.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import ua_parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class LoginNotificationConfig {

    @Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }

    @Bean(name="GeoIPCity")
    public DatabaseReader databaseReader() throws IOException {
//        File database = ResourceUtils
//                .getFile("classpath:maxmind/GeoLite2-City.mmdb");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/maxmind/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(inputStream)
                .build();
    }
}
