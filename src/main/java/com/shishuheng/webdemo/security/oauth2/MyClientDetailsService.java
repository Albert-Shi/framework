package com.shishuheng.webdemo.security.oauth2;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * @author shishuheng
 * @date 2020/3/6 3:51 下午
 */
public class MyClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return null;
    }
}
