package org.eureka.stockAnalytics.config;

import feign.RequestInterceptor;
import org.eureka.stockAnalytics.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    LoginService loginService;

    @Autowired
    public FeignConfig(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public RequestInterceptor getBearerRequestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + loginService.getBearerToken());
    }
}
