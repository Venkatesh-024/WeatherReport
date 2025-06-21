package com.twg.springboot.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.twg.springboot.weatherapp.service.WeatherService;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:5173")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeather(@RequestParam String city) {
        Map<String, Object> weatherData = weatherService.getWeatherData(city);
        return ResponseEntity.ok(weatherData);
    }
}
