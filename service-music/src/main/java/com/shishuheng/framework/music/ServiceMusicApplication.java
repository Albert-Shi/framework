package com.shishuheng.framework.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shishuheng
 * @date 2020年 03月 10日 22:25:46
 */
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.shishuheng.framework"})
@SpringBootApplication(scanBasePackages = {"com.shishuheng.framework"})
public class ServiceMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMusicApplication.class, args);
    }
}
