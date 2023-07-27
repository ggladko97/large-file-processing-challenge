package com.ehladkevych.challenge.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TemperatureDataResult {
    String city;
    String year;
    Float average;
}
