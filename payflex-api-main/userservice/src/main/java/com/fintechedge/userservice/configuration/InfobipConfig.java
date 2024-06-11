package com.fintechedge.userservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfobipConfig {

    @Value("${infobip.api.key}")
    private String apiKey;

    @Value("${infobip.api.base.url}")
    private String baseUrl;

    private String getApiKey() {
        return apiKey;
    }

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
