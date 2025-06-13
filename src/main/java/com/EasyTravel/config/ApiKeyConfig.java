package com.EasyTravel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiKeyConfig {

    private String googleKey;
    private String openaiKey;
    private String weatherKey;

    // Getters and Setters
    public String getGoogleKey() {
        return googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

    public String getOpenaiKey() {
        return openaiKey;
    }

    public void setOpenaiKey(String openaiKey) {
        this.openaiKey = openaiKey;
    }

    public String getWeatherKey() {
        return weatherKey;
    }

    public void setWeatherKey(String weatherKey) {
        this.weatherKey = weatherKey;
    }
}

@Configuration
@ConfigurationProperties(prefix = "gpt.api")
class GptConfig {

    private String key;
    private String model;
    private String itineraryModel;
    private int timeout;

    // Getters and Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getItineraryModel() {
        return itineraryModel;
    }

    public void setItineraryModel(String itineraryModel) {
        this.itineraryModel = itineraryModel;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

@Configuration
@ConfigurationProperties(prefix = "external")
class ExternalApiConfig {

    private GoogleConfig google;
    private WeatherConfig weather;

    // Getters and Setters
    public GoogleConfig getGoogle() {
        return google;
    }

    public void setGoogle(GoogleConfig google) {
        this.google = google;
    }

    public WeatherConfig getWeather() {
        return weather;
    }

    public void setWeather(WeatherConfig weather) {
        this.weather = weather;
    }

    public static class GoogleConfig {
        private String geocodeUrl;

        public String getGeocodeUrl() {
            return geocodeUrl;
        }

        public void setGeocodeUrl(String geocodeUrl) {
            this.geocodeUrl = geocodeUrl;
        }
    }

    public static class WeatherConfig {
        private String forecastUrl;

        public String getForecastUrl() {
            return forecastUrl;
        }

        public void setForecastUrl(String forecastUrl) {
            this.forecastUrl = forecastUrl;
        }
    }
}