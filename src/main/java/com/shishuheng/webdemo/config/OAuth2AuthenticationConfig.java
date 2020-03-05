//package com.shishuheng.webdemo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.stereotype.Component;
//
///**
// * @author shishuheng
// * @date 2020/3/4 5:23 下午
// */
//@Component
//@EnableAuthorizationServer
//public class OAuth2AuthenticationConfig extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//    @Autowired
//    private JwtTokenStore tokenStore;
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("test-key");
//        return converter;
//    }
//
//    @Bean
//    public JwtTokenStore jwtTokenStore() {
//        JwtTokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter);
//        return tokenStore;
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("justForFun").secret("123456").scopes("read", "update", "delete", "add").authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials");
//    }
//}
