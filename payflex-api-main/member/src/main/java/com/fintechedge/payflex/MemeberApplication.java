package com.fintechedge.payflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.fintechedge.payflex.repository")
public class MemeberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeberApplication.class, args);
	}

}
