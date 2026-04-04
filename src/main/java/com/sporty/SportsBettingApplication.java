package com.sporty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SportsBettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsBettingApplication.class, args);
	}

}
