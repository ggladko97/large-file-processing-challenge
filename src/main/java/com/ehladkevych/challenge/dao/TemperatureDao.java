package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.Temperature;
import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import com.ehladkevych.challenge.exception.DbInternalException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@Transactional
public class TemperatureDao {

    private final TemperatureRepository temperatureRepository;
    private final JdbcTemplate jdbcTemplate;

    public TemperatureDao(TemperatureRepository temperatureRepository, JdbcTemplate jdbcTemplate) {
        this.temperatureRepository = temperatureRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(Set<Temperature> temperatureData) {
        try {
            //tdb: improve
            for (Temperature t : temperatureData) {
                temperatureRepository.save(t);
            }
        } catch (Exception e) {
            throw new DbInternalException("Failed to save into the DB", e);
        }
    }

    public List<TemperatureWithAverage> getAverageForCity(String city) {
        try {
            return temperatureRepository.calculateAvgForCity(city);
        } catch (Exception e) {
            throw new DbInternalException("Failed to retrieve from the DB", e);
        }
    }

    public void cleanup() {
        try {
            temperatureRepository.deleteAll();
        } catch (Exception e) {
            throw new DbInternalException("Failed to remove all from the DB", e);
        }
    }

    public void setupDb() {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS temperature_data;");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS temperature_data(\n" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   city TEXT NOT NULL,\n" +
                    "   year INTEGER,\n" +
                    "   temperature REAL\n" +
                    ");");
        } catch (Exception e) {
            throw new DbInternalException("Failed to set up the db", e);
        }
    }
}
