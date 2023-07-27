package com.ehladkevych.challenge.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name ="temperature_data")
public class Temperature {

    @Id
    private Long id;
    private String city;
    private Integer year;
    private Float temperature;

}
