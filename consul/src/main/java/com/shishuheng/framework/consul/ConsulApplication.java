package com.shishuheng.framework.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shishuheng
 * @date 2020/3/19 5:31 下午
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulApplication.class, args);
    }

    @RequestMapping("/echo")
    public String echo(@RequestParam(value = "value", required = false) String value) {
        if (null == value) {
            return "test";
        } else {
            return value;
        }
    }
}
