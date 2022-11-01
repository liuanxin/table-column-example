package com.github.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

@Configuration
@ConditionalOnClass({ Filter.class })
public class GlobalWebConfig {

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterFilter() {
        FilterRegistrationBean<CharacterEncodingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName(), true));
        return filterBean;
    }
}
