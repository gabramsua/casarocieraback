package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.boot.controller", "com.boot.dao", "com.boot.services", "com.boot.repository", "com.boot.model", "com.boot.pojo"
})
@EnableJpaRepositories(basePackages = "com.boot.repository")
@EntityScan(basePackages = "com.boot.model")
public class CasaRocieraBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasaRocieraBackApplication.class, args);
	}

}
