package com.ehladkevych.challenge.dao.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="temperature_data")
public class Temperature {

    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMP_SEQ")
    @SequenceGenerator(name = "TEMP_SEQ", sequenceName = "TEMP_SEQ", allocationSize = 1)
    private Long id;
    private String city;
    private Integer year;
    private Float temperature;

}
