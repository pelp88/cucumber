package ru.coderiders.cucumber_emulator.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadingDtoForCreating extends AbstractDto {
    private UUID sensorSerialNumber;
    private Double latitude;
    private Double longitude;
}
