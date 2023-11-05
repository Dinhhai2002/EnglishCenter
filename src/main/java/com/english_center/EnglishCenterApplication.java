package com.english_center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.english_center"})
@EnableAutoConfiguration
@EnableScheduling
public class EnglishCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnglishCenterApplication.class, args);
	}

}
