package com.ehladkevych.challenge;

import org.komamitsu.spring.data.sqlite.EnableSqliteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSqliteRepositories
@EnableScheduling
public class LargeFileReadingChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LargeFileReadingChallengeApplication.class, args);
	}

}
