package ru.coderiders.cucumber.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coderiders.cucumber.mapper.UserMapper;
import ru.coderiders.cucumber.rest.dto.AuthDto;
import ru.coderiders.cucumber.rest.dto.UserRsDto;
import ru.coderiders.cucumber.service.details.CucumberUserDetails;
import ru.coderiders.cucumber.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserRsDto> authenticate(@RequestBody @Valid AuthDto authDto) {
        var login = authDto.getLogin();
        var password = authDto.getPassword();
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, password));

        var user = ((CucumberUserDetails) authentication.getPrincipal()).getUser();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(user))
                .body(userMapper.toDto(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) {
        var securityContextLogoutHandler = new SecurityContextLogoutHandler();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        securityContextLogoutHandler.logout(
                httpServletRequest,
                httpServletResponse,
                authentication);

        return ResponseEntity.ok().body(authentication.getPrincipal());
    }
}

