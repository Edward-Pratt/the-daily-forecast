package com.edwardpratt.thedailyforecast.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherCurrent {

    private LocalDateTime time;
    private Double temperature;
    private Double wind_speed;

    public WeatherCurrent(LocalDateTime time, Double temperature, Double wind_speed) {
        this.time = time;
        this.temperature = temperature;
        this.wind_speed = wind_speed;

    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTimeString() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDateString() {
        return time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return wind_speed;
    }
}
