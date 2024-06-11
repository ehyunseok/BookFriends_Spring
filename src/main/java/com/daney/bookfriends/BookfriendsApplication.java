package com.daney.bookfriends;

import com.daney.bookfriends.config.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties(ApiConfig.class)
public class BookfriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookfriendsApplication.class, args);
	}


}
