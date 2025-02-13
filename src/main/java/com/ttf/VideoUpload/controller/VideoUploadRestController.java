package com.ttf.VideoUpload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VideoUploadRestController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    @Value("${spring.application.name}")
    private String appName;

    public VideoUploadRestController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }


    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Ciao da: " + appName;
    }

    @GetMapping("/getAll")
    public List<String> getAllServices() {
        RestTemplate restTemplate = new RestTemplate();
        String[] serviceNames = {"VIDEOUPLOAD", "VIDEOTRANSCODING", "COMMENTS", "USERS", "STREAMING"};
        List<String> allResponses = new ArrayList<>();

        for (String serviceName : serviceNames) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            if (!serviceInstances.isEmpty()) {
                ServiceInstance serviceInstance = serviceInstances.get(0);
                String serviceResponse = restTemplate.getForObject(serviceInstance.getUri() + "/helloWorld", String.class);
                allResponses.add("Response from " + serviceName + ": " + serviceResponse);
            } else {
                allResponses.add("Service " + serviceName + " not found.");
            }
        }
        return allResponses;
    }
}
