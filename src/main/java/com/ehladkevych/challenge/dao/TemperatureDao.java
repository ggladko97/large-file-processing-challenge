package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.Temperature;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class TemperatureDao {

    private final TemperatureRepository temperatureRepository;
    private final JdbcTemplate jdbcTemplate;

    public TemperatureDao(TemperatureRepository temperatureRepository, JdbcTemplate jdbcTemplate) {
        this.temperatureRepository = temperatureRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(Set<Temperature> temperatureData) {
        temperatureRepository.saveAll(temperatureData);
    }

    public List<TemperatureRepository.TemperatureWithAverage> getAverageForCity(String city) {
        return temperatureRepository.calculateAvgForCity(city);
    }

    public void cleanup() {
        temperatureRepository.deleteAll();
    }

    public void setupDb() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS temperature_data;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS temperature_data(\n" +
                "   id INTEGER PRIMARY KEY NOT NULL,\n" +
                "   city TEXT NOT NULL,\n" +
                "   year INTEGER,\n" +
                "   temperature REAL\n" +
                ");");
    }
}
