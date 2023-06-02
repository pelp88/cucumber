package ru.coderiders.cucumber_emulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber_emulator.rest.api.SensorApi;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRqDto;
import ru.coderiders.cucumber_emulator.rest.dto.SensorRsDto;
import ru.coderiders.cucumber_emulator.service.SensorService;

@RestController
@RequiredArgsConstructor
public class SensorController implements SensorApi {
    private final SensorService sensorService;

    @Override
    public ResponseEntity<SensorRsDto> create(SensorRqDto sensorRqDto) {
        var sensor = sensorService.create(sensorRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }

    @Override
    public Page<SensorRsDto> findAll(Pageable pageable) {
        return sensorService.findAll(pageable);
    }

    @Override
    public SensorRsDto update(Long sensorId, SensorRqDto sensorRqDto) {
        return sensorService.update(sensorId, sensorRqDto);
    }

    @Override
    public SensorRsDto findById(Long sensorId) {
        return sensorService.findById(sensorId);
    }

    @Override
    public ResponseEntity<Void> delete(Long sensorId) {
        sensorService.delete(sensorId);
        return ResponseEntity.accepted().build();
    }
}
