package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.Temperature;
import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import com.ehladkevych.challenge.dto.TemperatureDataResult;
import com.ehladkevych.challenge.entity.TemperatureData;

import java.util.List;
import java.util.stream.Collectors;

public class TemperatureConverter {

    public static Temperature toTemperature(TemperatureData temperatureData) {
        final Temperature temperature = new Temperature();
        temperature.setCity(temperatureData.getCity());
        temperature.setYear(Integer.valueOf(temperatureData.getYear()));
        temperature.setTemperature(temperatureData.getTemperature());
        return temperature;
    }

    public static List<TemperatureDataResult> fromTemperature(List<TemperatureWithAverage> averageForCity) {
        return averageForCity.stream().map(TemperatureConverter::mapTempratureWithAverageRow).collect(Collectors.toList());
    }

    private static TemperatureDataResult mapTempratureWithAverageRow(TemperatureWithAverage row) {
        return TemperatureDataResult.builder().city(row.getCity())
                .year(String.valueOf(row.getYear()))
                .average(row.getAverage())
                .build();
    }
}
