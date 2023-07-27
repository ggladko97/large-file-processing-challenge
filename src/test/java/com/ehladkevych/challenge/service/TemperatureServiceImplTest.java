package com.ehladkevych.challenge.service;

import com.ehladkevych.challenge.dao.TemperatureDao;
import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import com.ehladkevych.challenge.dto.TemperatureDataResult;
import com.ehladkevych.challenge.io.FileManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceImplTest {

    @Mock
    private TemperatureDao temperatureDao;
    @Mock
    private FileManager fileManager;

    @InjectMocks
    private TemperatureServiceImpl temperatureServiceImpl;

    @Test
    void shouldNotLoadTemperatureWhileFileNotChanged() {
        //given
        String city = "Wro";
        final int year = 2023;
        final float average = 15.0f;
        when(fileManager.shouldReloadAndRecalculate(any()))
                .thenReturn(false);
        when(temperatureDao.getAverageForCity(city))
                .thenReturn(List.of(new TemperatureWithAverage(city, year, average)));
        final List<TemperatureDataResult> expectedResult = List.of(TemperatureDataResult.builder()
                .average(average)
                .year(String.valueOf(year))
                .city(city)
                .build());

        //when
        final List<TemperatureDataResult> actualResult = temperatureServiceImpl.getAverage(city);

        //then
        assertThat(actualResult, is(expectedResult));
        verifyNoMoreInteractions(temperatureDao);
    }
}
