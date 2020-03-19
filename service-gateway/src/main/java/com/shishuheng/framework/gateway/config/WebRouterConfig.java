package com.shishuheng.framework.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author shishuheng
 * @date 2020/3/9 2:57 下午
 */
@Configuration
@RestController
public class WebRouterConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
//                .route(p -> p.path("/get").filters(f -> f.addRequestHeader("Hello", "World")).uri("http://www.baidu.com"))
//                .route(p ->
//                        p.host("*.hystrix.com").filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("forward:/fallback"))).uri("http://httpbin.org"))
                // 此处为示例用代码配置 主要配置还是使用配置文件
                .route(p -> p
                        .path("/authentication/**")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("authen")
                                        .setFallbackUri("forward:/fallback"))
                                .stripPrefix(1))
                        /* 以下的两种配置方式都是支持的 */
                        .uri("lb://service-authentication")
                        .uri("http://localhost:9020"))
                .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
