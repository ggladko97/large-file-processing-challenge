package com.ehladkevych.challenge.service;

import com.ehladkevych.challenge.dao.TemperatureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SqliteDbManagerService implements ApplicationListener<ContextRefreshedEvent> {

    private final TemperatureDao temperatureDao;
    private final TemperatureService temperatureService;

    @Autowired
    public SqliteDbManagerService(TemperatureDao temperatureDao, TemperatureService temperatureService) {
        this.temperatureDao = temperatureDao;
        this.temperatureService = temperatureService;
    }

    @Async
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        temperatureService.refresh();
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        System.out.println("Refreshing the DB in the background");
        temperatureDao.setupDb();
    }
}
