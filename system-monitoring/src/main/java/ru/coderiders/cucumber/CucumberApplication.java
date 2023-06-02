package ru.coderiders.cucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ru.coderiders.cucumber", "ru.coderiders.weathergetter"})
@EnableFeignClients(basePackages = "ru.coderiders.weathergetter")
@EnableScheduling
public class CucumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(CucumberApplication.class, args);
	}

}
