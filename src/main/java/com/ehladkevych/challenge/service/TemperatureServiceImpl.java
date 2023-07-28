package com.ehladkevych.challenge.service;

import com.ehladkevych.challenge.dao.TemperatureConverter;
import com.ehladkevych.challenge.dao.TemperatureDao;
import com.ehladkevych.challenge.dao.entity.TemperatureWithAverage;
import com.ehladkevych.challenge.dto.TemperatureDataResult;
import com.ehladkevych.challenge.entity.TemperatureData;
import com.ehladkevych.challenge.exception.DataProcessingException;
import com.ehladkevych.challenge.io.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TemperatureServiceImpl implements TemperatureService {

    private final FileManager fileManager;
    private static final int CHUNK_SIZE = 500;
    private static final int MAX_CHUNKS = 8;
    private final TemperatureDao temperatureDao;


    @Autowired
    public TemperatureServiceImpl(FileManager fileManager,
                                  TemperatureDao temperatureDao) {
        this.fileManager = fileManager;
        this.temperatureDao = temperatureDao;
    }

    @Override
    public List<TemperatureDataResult> getAverage(String city) {
        File file = fileManager.createFile();
        if (fileManager.shouldReloadAndRecalculate(file)) {
            log.info("File has been changed, reloading the data");
            temperatureDao.cleanup();
            loadTemperature();
            log.info("Data has been re-loaded");
            return getAverageFromDB(city);
        }
        log.info("Weather file hasn't been updated since the last run, " +
                "getting the result from the DB");
        return getAverageFromDB(city);
    }

    @Override
    public void refresh() {
        File file = fileManager.createFile();
        if (fileManager.shouldReloadAndRecalculate(file)) {
            log.info("File has been changed, reloading the data");
            temperatureDao.cleanup();
            loadTemperature();
        }
        log.info("Weather file hasn't been updated since the last run");
    }

    private void loadTemperature() {
        File file = fileManager.createFile();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int chunkCounter = 0;
            String line;
            List<Set<TemperatureData>> chunksToProcess = new ArrayList<>();
            Set<TemperatureData> chunk = new HashSet<>();

            while ((line = br.readLine()) != null) {
                if (chunkCounter < CHUNK_SIZE) {
                    chunk.add(parseLine(line));
                    chunkCounter++;
                } else {
                    if (MAX_CHUNKS > chunksToProcess.size()) {
                        chunksToProcess.add(chunk);
                    } else {
                        processChunks(chunksToProcess);
                        chunk = new HashSet<>();
                        chunksToProcess = new ArrayList<>();
                    }
                }
            }
        } catch (IOException e) {
            throw new DataProcessingException("Unable to process the input CSV file", e);
        }

        fileManager.updateLastModifiedForFile(file);
    }

    void processChunks(List<Set<TemperatureData>> chunksToProcess) {
        chunksToProcess
                .stream()
                .map(td -> td.stream().map(TemperatureConverter::toTemperature).collect(Collectors.toSet()))
                .forEach(temperatureDao::saveAll);
    }

    private List<TemperatureDataResult> getAverageFromDB(String city) {
        final List<TemperatureWithAverage> averageForCity = temperatureDao.getAverageForCity(city);
        return TemperatureConverter.fromTemperature(averageForCity);
    }

    private TemperatureData parseLine(String line) {
        final String[] row = line.split(CSV_SEMICOLON_DELIMITER);
        try {
            final String year = row[1].substring(0, 4);
            return new TemperatureData(row[0], year, Float.valueOf(row[2]));
        } catch (IllegalArgumentException iae) {
            throw new DataProcessingException("Input file contains illegal line: " + line, iae);
        }
    }
}
