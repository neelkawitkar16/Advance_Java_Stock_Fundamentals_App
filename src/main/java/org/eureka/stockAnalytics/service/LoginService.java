package org.eureka.stockAnalytics.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    private String loginURL;
    private RestTemplate restTemplate;

    //@Value annotation is used to absorb property value from application.properties/application.yaml file
    public LoginService(@Value("${client.stock-calculations.url}") String loginURL,
                        @Value("${client.login.username}") String userName,
                        @Value("${client.login.password}") String password) {
        this.loginURL = loginURL + "/login";

        restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(userName, password));
    }

    //hit the login service and get the bearer token
    public String getBearerToken() {
        ResponseEntity<String> response = restTemplate.exchange(loginURL, HttpMethod.POST, null, String.class);
        return response.getBody();
    }
}
