package com.ehladkevych.challenge.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemperatureWithAverage {

    private String city;
    private Integer year;
    private Float average;

}
