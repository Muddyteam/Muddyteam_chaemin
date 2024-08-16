package com.example.muddyteam_chaemin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.muddyteam_chaemin.Repository")
@EntityScan(basePackages = "com.example.muddyteam_chaemin.domain")
public class MuddyteamChaeminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuddyteamChaeminApplication.class, args);
    }

    @Bean(name = "customRestTemplate")
    public RestTemplate customRestTemplate() {
        return new RestTemplate();
    }
}

