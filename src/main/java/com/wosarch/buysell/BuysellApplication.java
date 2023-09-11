package com.wosarch.buysell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BuysellApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuysellApplication.class, args);
	}

}
