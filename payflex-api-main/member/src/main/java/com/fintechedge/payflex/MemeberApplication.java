package com.fintechedge.payflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories("com.fintechedge.payflex.repository")
@ComponentScan(basePackages = {"com.fintechedge.payflex"})
public class MemeberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeberApplication.class, args);
	}

}
