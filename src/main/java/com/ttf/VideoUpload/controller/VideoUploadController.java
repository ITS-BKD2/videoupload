package com.ttf.VideoUpload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping
public class VideoUploadController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    @Value("${spring.application.name}")
    private String appName;

    public VideoUploadController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello world from " + appName;
    }
//
//    @GetMapping("/showServices")
//    public String showServices() {
//
//        String[] services = {"VideoTranscoding", "VideoUpload", "VideoUpload", "Streaming", "Comments"};
//
//        for (String service : services) {
//
//            ServiceInstance serviceInstance = discoveryClient.getInstances(service).get(0);
//            String ServiceResponse = restClient.get()
//                    .uri(serviceInstance.getUri() + "/helloWorld")
//                    .retrieve()
//                    .body(String.class);
//            return ServiceResponse;
//
//        }
//        return null;
//
//    }

    @GetMapping("/gethello")
    public String callHelloWorld(String word) {

        RestTemplate restTemplate = new RestTemplate();
        //List<ServiceInstance> serviceInstance = discoveryClient.getInstances(appName);

        return restTemplate.getForObject("/helloworld", String.class);

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
                String serviceResponse = restTemplate.getForObject(serviceInstance.getUri() + "/helloworld", String.class);
                allResponses.add("Response from " + serviceName + ": " + serviceResponse);
            } else {
                allResponses.add("Service " + serviceName + " not found.");
            }
        }
        return allResponses;
    }

}