package com.shishuheng.framework.common.module.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 23:53:11
 */
public class OAuth2RequestInterceptor implements RequestInterceptor {
    private static final String HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private final OAuth2RestTemplate restTemplate;

    public OAuth2RequestInterceptor(OAuth2RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "OAuth2RequestTemplate 不能为空");
        this.restTemplate = restTemplate;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String authStr = restTemplate.getAccessToken().toString();
        String value = BEARER + " " + authStr;
        requestTemplate.header(HEADER, value);
    }
}
