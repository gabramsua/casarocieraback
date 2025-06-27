package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.boot.controller", "com.boot.dao", "com.boot.services", "com.boot.repository", "com.boot.model", "com.boot.pojo"
//		"com.boot"
})
public class CasaRocieraBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasaRocieraBackApplication.class, args);
	}

}
