package com.shishuheng.framework.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author shishuheng
 * @date 2020年 03月 10日 22:25:46
 */
@EnableEurekaClient
@SpringBootApplication
public class ServiceMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMusicApplication.class, args);
    }
}
