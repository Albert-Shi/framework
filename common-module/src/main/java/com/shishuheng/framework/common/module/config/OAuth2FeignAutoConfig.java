package com.shishuheng.framework.common.module.config;

import com.shishuheng.framework.common.module.security.OAuth2RequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 23:48:56
 */
@Configuration
public class OAuth2FeignAutoConfig {
    @Bean
    public RequestInterceptor oath2RequestInterceptor(@Qualifier("frameworkAuth2RestTemplate") OAuth2RestTemplate template) {
        return new OAuth2RequestInterceptor(template);
    }
}
