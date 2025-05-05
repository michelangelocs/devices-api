package com.example.devicesapi;

import com.example.devicesapi.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DatabaseProperties.class)
@SpringBootApplication
public class DevicesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevicesApiApplication.class, args);
    }

}
