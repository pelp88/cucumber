package ru.coderiders.cucumber_emulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ru.coderiders.cucumber_emulator", "ru.coderiders.weathergetter"})
@EnableFeignClients(basePackages = "ru.coderiders.weathergetter")
@EnableScheduling
public class CucumberEmulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CucumberEmulatorApplication.class, args);
    }

}
