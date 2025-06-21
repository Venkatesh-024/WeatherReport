package com.twg.springboot.weatherapp.dto;

public class WeatherResponse {
    private String city;
    private String description;
    private double temperature;
    private double feelsLike;
    private double humidity;

    // Constructors
    public WeatherResponse() {}

    public WeatherResponse(String city, String description, double temperature, double feelsLike, double humidity) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
    }

    // Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
