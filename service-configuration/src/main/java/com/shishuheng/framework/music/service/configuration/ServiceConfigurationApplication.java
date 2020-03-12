package com.shishuheng.framework.music.service.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ServiceConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConfigurationApplication.class, args);
    }

}
