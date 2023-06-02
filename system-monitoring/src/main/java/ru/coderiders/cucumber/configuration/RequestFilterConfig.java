package ru.coderiders.cucumber.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.coderiders.cucumber.filter.ControllerFilter;

@Configuration
public class RequestFilterConfig {

    private final String[] patternsArray = {
            "/plant/*", "/plant", "/task/*", "/task", "/field/*", "/field", "/user/*", "/user"
    };

    @Bean
    public FilterRegistrationBean<ControllerFilter> loggingFilter() {
        FilterRegistrationBean<ControllerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ControllerFilter());
        registrationBean.addUrlPatterns(patternsArray);
        return registrationBean;
    }
}
