package com.shishuheng.framework.common.module.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.Arrays;

/**
 * @author shishuheng
 * @date 2020年 03月 13日 22:46:34
 * <p>
 * 参考自 https://yq.aliyun.com/articles/585159
 */
@Configuration
public class OAuth2ClientFeignConfig {
    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Bean(name = "frameworkClientCredentialsResourceDetails")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(oAuth2Properties.getId());
        details.setAccessTokenUri(oAuth2Properties.getAccessTokenUri());
        details.setClientId(oAuth2Properties.getClientId());
        details.setClientSecret(oAuth2Properties.getClientSecret());
        details.setScope(Arrays.asList(oAuth2Properties.getScope()));
        details.setClientAuthenticationScheme(AuthenticationScheme.valueOf(oAuth2Properties.getClientAuthenticationScheme()));
        return details;
    }

    @Bean(name = "frameworkAuth2RestTemplate")
    public OAuth2RestTemplate auth2RestTemplate(@Qualifier(value = "frameworkClientHttpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails(), new DefaultOAuth2ClientContext());
        oAuth2RestTemplate.setRequestFactory(clientHttpRequestFactory);
        return oAuth2RestTemplate;
    }

    @Bean(name = "frameworkClientHttpRequestFactory")
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        return new Netty4ClientHttpRequestFactory();
    }
}
