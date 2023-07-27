package com.ehladkevych.challenge.entity;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class TemperatureKey {
    String city;
    int year;
}
