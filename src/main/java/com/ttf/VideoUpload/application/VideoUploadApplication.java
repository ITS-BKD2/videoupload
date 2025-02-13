package com.ttf.VideoUpload.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ttf.VideoUpload")
public class VideoUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoUploadApplication.class, args);
    }

}
