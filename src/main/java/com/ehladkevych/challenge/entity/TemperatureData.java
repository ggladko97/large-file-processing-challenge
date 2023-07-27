package com.ehladkevych.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@AllArgsConstructor
public class TemperatureData {
    String city;
    String year;
    Float temperature;
}
