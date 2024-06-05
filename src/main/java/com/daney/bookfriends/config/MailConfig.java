package com.daney.bookfriends.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:email-config.properties")
public class MailConfig {

    @Value("${mail.password}")
    private String emailPassword;

    public String getEmailPassword() {
        return emailPassword;
    }
}
