
package ru.coderiders.weathergetter.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.weathergetter.dto.WeatherDto;
import ru.coderiders.weathergetter.rest.feignclient.WeatherFeignClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherGetterController {

    private final WeatherFeignClient weatherFeignClient;

    public ResponseEntity<List<WeatherDto>> getWeather(Double latitude, Double longitude) {
        var owmRequestBody = weatherFeignClient.getWeather(latitude, longitude).getBody();
        try {
            var mapper = new ObjectMapper();
            JsonNode list = mapper.readTree(owmRequestBody);
            var output = new ArrayList<WeatherDto>();
            var iter = list.path("list").elements();

            while (iter.hasNext()) {
                var temp = iter.next().toString();
                output.add(new ObjectMapper().readValue(temp, WeatherDto.class));
            }

            return ResponseEntity.ok(output);
        } catch (Exception exception) {
            return ResponseEntity.status(500).build();
        }
    }

}

