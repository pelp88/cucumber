package ru.coderiders.weathergetter.rest.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "owm-client", url = "${feign.url}")
public interface WeatherFeignClient {

    @GetMapping("/forecast?lat={latitude}&lon={longitude}" +
            "&appid=18bfb493d78012697862a9854db15360" +
            "&units=metric" +
            "&lang=ru")
    ResponseEntity<String> getWeather(@PathVariable Double latitude, @PathVariable Double longitude);
}
