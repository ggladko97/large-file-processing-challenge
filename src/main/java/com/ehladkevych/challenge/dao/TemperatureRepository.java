package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.Temperature;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {

    @Query(value = "SELECT new TemperatureWithAverage(city, `year`, avg(temperature)) FROM temperature_data where city = :city " +
            "group by city, year order by year desc")
    List<TemperatureWithAverage> calculateAvgForCity(@Param("city") String city);

    @Data
    @AllArgsConstructor
    class TemperatureWithAverage {

        private String city;
        private Integer year;
        private Float average;

    }
}
