package com.example.frontend;

import com.example.frontend.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableCircuitBreaker
public class FrontendApplication {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return restTemplate.exchange("http://backend/api/v1/products",
                                     HttpMethod.GET,
                                     null,
                                     new ParameterizedTypeReference<List<Product>>() {})
                           .getBody();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/products_str")
    public String getProductsStr() {
        return restTemplate.exchange("http://backend/api/v1/products",
                                     HttpMethod.GET,
                                     null,
                                     String.class)
                           .getBody();
    }

    public String fallback() {
        return "error";
    }
}
