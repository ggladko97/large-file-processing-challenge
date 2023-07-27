package com.ehladkevych.challenge.service;

import com.ehladkevych.challenge.dto.TemperatureDataResult;

import java.util.List;

public interface TemperatureService {

    String CSV_SEMICOLON_DELIMITER = ";";

    void loadTemperature();

    List<TemperatureDataResult> getAverage(String city);
}
