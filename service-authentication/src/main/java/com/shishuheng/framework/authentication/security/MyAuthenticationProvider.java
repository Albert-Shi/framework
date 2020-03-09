package com.shishuheng.framework.authentication.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author shishuheng
 * @date 2020年 03月 02日 20:08:54
 */
public class MyAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // todo 做一些校验
        logger.info("打印一些东西，证明执行过此方法");
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
