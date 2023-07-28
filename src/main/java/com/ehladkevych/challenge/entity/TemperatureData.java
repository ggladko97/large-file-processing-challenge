package com.ehladkevych.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Getter
@AllArgsConstructor
public class TemperatureData {
    String city;
    String year;
    Float temperature;
}
