package com.daney.bookfriends;

import com.daney.bookfriends.config.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan/*(basePackages = "com.daney.bookfriends.entity")*/
@EnableConfigurationProperties(ApiConfig.class)	//국중 api key
public class BookfriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookfriendsApplication.class, args);
	}


}
