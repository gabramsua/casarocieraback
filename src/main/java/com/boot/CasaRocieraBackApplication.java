package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = {
		"com.boot.controller", "com.boot.dao", "com.boot.service", "com.boot.repository", "com.boot.model"
//		"com.boot.controller", "dao", "service", "repository", "model"
//		"com.boot"
})
public class CasaRocieraBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasaRocieraBackApplication.class, args);
	}

}
