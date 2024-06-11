package com.daney.bookfriends.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "api")
@Configuration
@Getter @Setter
public class ApiConfig {

    private String key;

}
