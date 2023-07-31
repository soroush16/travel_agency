package com.travelagency.controller;

import com.travelagency.security.config.JwtTokenUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SampleBeanRestControllerTestConfig {
    @Bean
    public JwtTokenUtil getJwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
