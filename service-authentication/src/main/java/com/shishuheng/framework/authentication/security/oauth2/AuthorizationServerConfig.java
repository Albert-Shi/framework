package com.shishuheng.framework.authentication.security.oauth2;

import com.shishuheng.framework.authentication.helper.common.GlobalProperties;
import com.shishuheng.framework.authentication.service.ClientService;
import com.shishuheng.framework.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author shishuheng
 * @date 2020/3/5 4:58 下午
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static String SIGNING_KEY = "ThisIsMyKey";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientService clientService;
    @Autowired
    private GlobalProperties globalProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private MyTokenEnhancer tokenEnhancer;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if (null != globalProperties.getSigningKey() && !"".equals(globalProperties.getSigningKey())) {
            SIGNING_KEY = globalProperties.getSigningKey();
        }
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter()));
        endpoints
                .userDetailsService(userService)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(chain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
    }
}
