package com.carrafasoft.carrafafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.carrafasoft.carrafafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class CarrafafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrafafoodApiApplication.class, args);
	}

}
