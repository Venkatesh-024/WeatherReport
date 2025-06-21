package com.twg.springboot.weatherapp.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WeatherService {

    private final String apiKey = "49985b0687e67f9046134a57ce130156"; // 🔁 Replace with your OpenWeatherMap API key

    private final String currentWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private final String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric";

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getWeatherData(String city) {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> currentWeather = fetchCurrentWeather(city);
        List<Map<String, Object>> forecast = fetchWeatherForecast(city);

        response.put("current", currentWeather);
        response.put("forecast", forecast);

        return response;
    }

    private Map<String, Object> fetchCurrentWeather(String city) {
        String url = String.format(currentWeatherUrl, city, apiKey);
        ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
        Map body = resp.getBody();

        Map<String, Object> result = new HashMap<>();
        result.put("city", city);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> main = (Map<String, Object>) body.get("main");
        Number temp = (Number) main.get("temp");
        Number pressure = (Number) main.get("pressure");
        Number humidity = (Number) main.get("humidity");

        @SuppressWarnings("unchecked")
        Map<String, Object> wind = (Map<String, Object>) body.get("wind");
        Number windSpeed = (Number) wind.get("speed");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) body.get("weather");
        String description = (String) weatherList.get(0).get("description");

        result.put("temperature", temp.doubleValue());
        result.put("pressure", pressure.doubleValue());
        result.put("humidity", humidity.doubleValue());
        result.put("windSpeed", windSpeed.doubleValue());
        result.put("description", description);

        return result;
    }
    

    private List<Map<String, Object>> fetchWeatherForecast(String city) {
        String url = String.format(forecastUrl, city, apiKey);
       

        ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
        Map body = resp.getBody();
         
        @SuppressWarnings("unchecked")

        List<Map<String, Object>> forecastList = (List<Map<String, Object>>) body.get("list");
        Map<String, List<Map<String, Object>>> groupedByDate = new TreeMap<>();

        for (Map<String, Object> item : forecastList) {
            String dt_txt = (String) item.get("dt_txt");
            String date = dt_txt.split(" ")[0];
            @SuppressWarnings("unchecked")

            Map<String, Object> main = (Map<String, Object>) item.get("main");
            Number temp = (Number) main.get("temp");
            @SuppressWarnings("unchecked")

            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) item.get("weather");
            String weather = (String) weatherList.get(0).get("main");

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("temp", temp.doubleValue());
            dayData.put("weather", weather);

            groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(dayData);
        }

        List<Map<String, Object>> forecast = new ArrayList<>();
        int count = 0;

        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedByDate.entrySet()) {
            if (count >= 5) break;

            String date = entry.getKey();
            List<Map<String, Object>> values = entry.getValue();

            double avgTemp = values.stream()
                    .mapToDouble(val -> (Double) val.get("temp"))
                    .average()
                    .orElse(0.0);

            String mainWeather = (String) values.get(0).get("weather");

            Map<String, Object> result = new HashMap<>();
            result.put("date", date);
            result.put("temp", Math.round(avgTemp * 100.0) / 100.0);
            result.put("weather", mainWeather);

            forecast.add(result);
            count++;
        }

        return forecast;
    }
}
