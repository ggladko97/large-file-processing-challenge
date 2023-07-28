package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.Temperature;
import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {

    @Query(value = "SELECT t.city as city, t.year as year, avg(t.temperature) as average FROM temperature_data t where t.city = :city " +
            "group by t.city, t.year order by t.year desc", resultSetExtractorClass = TemperatureAverageResultSetExtractor.class)
    List<TemperatureWithAverage> calculateAvgForCity(@Param("city") String city);

}
