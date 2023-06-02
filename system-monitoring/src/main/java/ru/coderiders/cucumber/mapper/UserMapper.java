package ru.coderiders.cucumber.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.coderiders.cucumber.entity.Field;
import ru.coderiders.cucumber.entity.User;
import ru.coderiders.cucumber.rest.dto.FieldRqDto;
import ru.coderiders.cucumber.rest.dto.FieldRsDto;
import ru.coderiders.cucumber.rest.dto.UserRqDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;

import javax.annotation.PostConstruct;

/**
 * Маппер для пользователя
 *
 * @author Anton Belyakov
 */
@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(UserRqDto.class, User.class);
        modelMapper.createTypeMap(User.class, UserRsDto.class);
    }

    public User toEntity(UserRqDto userRqDto) {
        return modelMapper.map(userRqDto, User.class);
    }

    public UserRsDto toDto(User user) {
        return modelMapper.map(user, UserRsDto.class);
    }
}
