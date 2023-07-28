package com.ehladkevych.challenge.dao;

import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureAverageResultSetExtractor implements ResultSetExtractor<List<TemperatureWithAverage>> {

    @Override
    public List<TemperatureWithAverage> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TemperatureWithAverage> result = new ArrayList<>();
        while (rs.next()) {
            final String city = rs.getString(1);
            final Integer year = rs.getInt(2);
            final Float average = rs.getFloat(3);
            result.add(new TemperatureWithAverage(city, year, average));
        }
        return result;
    }
}
