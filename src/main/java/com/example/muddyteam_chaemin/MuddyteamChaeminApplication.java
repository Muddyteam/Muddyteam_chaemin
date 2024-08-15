package com.example.muddyteam_chaemin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MuddyteamChaeminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuddyteamChaeminApplication.class, args);
    }

    @Bean(name = "customRestTemplate")
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}

