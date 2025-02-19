package com.ttf.VideoUpload.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/test")
@RestController
public class TestResilience {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;


    public TestResilience(RestTemplate restTemplate, EurekaDiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/callUserService")
    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "userServiceFallback")
    public String callUserService() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("USER");

        if (!serviceInstances.isEmpty()) {
            String serviceUrl = serviceInstances.get(0).getUri().toString();
            return restTemplate.getForObject(serviceUrl + "/helloworld", String.class);
        }

        throw new RuntimeException("User service non trovato"); // Trigger fallback
    }

    public String userServiceFallback(Throwable throwable) {
        return "Fai qlcs in caso servizio user non sia disponibile";
    }

}
