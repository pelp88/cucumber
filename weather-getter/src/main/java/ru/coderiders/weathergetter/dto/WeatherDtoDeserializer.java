package ru.coderiders.weathergetter.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Десериалайзер для JSON, получаемого от OpenWeatherMap, в WeatherDto
 *
 * @author Nikiforov Artyom - pelp88@outlook.com
 */
public class WeatherDtoDeserializer extends JsonDeserializer<WeatherDto> {
    @Override
    public WeatherDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        WeatherDto weatherDto = new WeatherDto();
        JsonNode jsonTree = jsonParser.readValueAsTree();

        // UnixTime to LocalDateTime
        var unixTime = Long.parseLong(jsonTree.get("dt").asText());

        weatherDto.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime),
                TimeZone.getDefault().toZoneId()));

        // Nested "main" object deserialization section
        JsonNode main = jsonTree.get("main");

        weatherDto.setAirTemperature(main.get("temp").asDouble());
        weatherDto.setHumidity(main.get("humidity").asInt());
        weatherDto.setPressure(main.get("pressure").asInt());

        //Nested "weather" object deserialization section
        JsonNode weather = jsonTree.get("weather").get(0);

        weatherDto.setDescription(weather.get("description").asText());
        weatherDto.setPrecipitationType(weather.get("main").asText());
        weatherDto.setPhenomenonCode(weather.get("id").asInt());
        weatherDto.setIconId(weather.get("icon").asText());

        //Other (not) nested objects deserialization section
        weatherDto.setCloudinessPercent(jsonTree.get("clouds").get("all").asInt());
        weatherDto.setStormPrediction(jsonTree.get("pop").asDouble());
        weatherDto.setWindSpeed(jsonTree.get("wind").get("speed").asDouble());

        if (jsonTree.toString().contains("rain")) {
            weatherDto.setPrecipitationAmount(jsonTree.get("rain").get("3h").asDouble());
        } else if (jsonTree.toString().contains("snow")) {
            weatherDto.setPrecipitationAmount(jsonTree.get("snow").get("3h").asDouble());
        } else {
            weatherDto.setPrecipitationAmount(0.0);
        }


        return weatherDto;
    }
}
