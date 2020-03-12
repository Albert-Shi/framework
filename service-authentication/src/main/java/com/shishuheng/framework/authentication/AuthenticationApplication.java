package com.shishuheng.framework.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author shishuheng
 */
@EnableEurekaClient
@EntityScan(basePackages = "com.shishuheng.framework")
@EnableFeignClients(basePackages = {"com.shishuheng.framework"})
@SpringBootApplication(scanBasePackages = {"com.shishuheng.framework"})
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
