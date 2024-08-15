package com.example.muddyteam_chaemin.Controller;

import com.example.muddyteam_chaemin.Service.WeatherService;
import com.example.muddyteam_chaemin.model.WeatherData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public List<WeatherData> getWeather() throws Exception {
        return weatherService.getFilteredWeatherData();
    }
}
