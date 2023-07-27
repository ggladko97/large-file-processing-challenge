package com.ehladkevych.challenge.api;

import com.ehladkevych.challenge.dto.TemperatureDataResult;
import com.ehladkevych.challenge.service.TemperatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityWeatherDataController {

    private final TemperatureService temperatureService;

    public CityWeatherDataController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping("/{city}/temperature")
    public ResponseEntity<List<TemperatureDataResult>> getTemperatureData(@PathVariable String city) {
        return ResponseEntity.ok(temperatureService.getAverage(city));
    }
}
