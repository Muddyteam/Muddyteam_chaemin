package com.example.muddyteam_chaemin.Controller;

import com.example.muddyteam_chaemin.Service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // 데이터를 수동으로 업데이트하는 엔드포인트
    @GetMapping("/api/update-weather")
    public String updateWeatherData() {
        try {
            weatherService.fetchAndSaveWeatherData();  // API 호출 및 DB 저장
            return "Weather data updated successfully!";
        } catch (Exception e) {
            return "Failed to update weather data: " + e.getMessage();
        }
    }
}
