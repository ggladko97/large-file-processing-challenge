package com.ehladkevych.challenge.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class TemperatureWithAverage {

    String city;
    Integer year;
    Float average;
}
