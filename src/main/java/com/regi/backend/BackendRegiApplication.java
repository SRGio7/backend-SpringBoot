package com.regi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class BackendRegiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendRegiApplication.class, args);
	}

}
