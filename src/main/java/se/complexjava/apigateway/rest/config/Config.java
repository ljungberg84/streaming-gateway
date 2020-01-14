package se.complexjava.apigateway.rest.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @LoadBalanced   // need to get all names of registered clients from Eureka server, service name instead of 'localhost'
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
