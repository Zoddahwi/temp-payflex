package com.fintechedge.payflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@ComponentScan(basePackages = {"com.fintechedge.payflex" , "com.fintechedge.kafka.service.email"})
public class MemeberApplication {
	public static void main(String[] args) {
		SpringApplication.run(MemeberApplication.class, args);
	}

}
