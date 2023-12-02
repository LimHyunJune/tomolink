package com.example.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class TomolinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomolinkApplication.class, args);
	}

	@Configuration
	@ComponentScan(basePackages = "com.example")
	public static class  ComponentScanConfig{}
}
