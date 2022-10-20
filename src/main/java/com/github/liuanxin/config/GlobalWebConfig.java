package com.github.liuanxin.config;

import com.github.liuanxin.query.annotation.EnableTableColumn;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

@EnableTableColumn
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
