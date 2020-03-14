package com.shishuheng.framework.common.module.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 22:59:13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2Properties {
    private String clientId;

    private String clientSecret;

    private String accessTokenUri;

    private String userAuthorizeUri;

    private String id;

    private String clientAuthenticationScheme;

    private String scope;
}
