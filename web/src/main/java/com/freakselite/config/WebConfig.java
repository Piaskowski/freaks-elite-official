package com.freakselite.config;

import com.freakselite.util.PageMappings;
import com.freakselite.util.ViewNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(PageMappings.HOME).setViewName(ViewNames.HOME);
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(@Value("${spring.datasource.url}") String url,
                                             @Value("${spring.datasource.username}") String username,
                                             @Value("${spring.datasource.password}") String password){
        return new SimpleJdbcInsert(new DriverManagerDataSource(url, username, password));
    }
}
