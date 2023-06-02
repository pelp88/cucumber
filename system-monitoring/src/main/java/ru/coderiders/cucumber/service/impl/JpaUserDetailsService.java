package ru.coderiders.cucumber.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.coderiders.cucumber.entity.User;
import ru.coderiders.cucumber.repository.UserRepository;
import ru.coderiders.cucumber.service.details.CucumberUserDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findAllWithNameLike(username)
                .stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь "
                        + username + " не найден!"));
        return new CucumberUserDetails(user);
    }
}
