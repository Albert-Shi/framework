package com.shishuheng.framework.authentication.security.oauth2;

import com.shishuheng.framework.common.module.domain.user.LoginUserInfo;
import com.shishuheng.framework.common.module.domain.user.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 19:58:27
 */
@Component
public class MyTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = null;
        if (authentication.getUserAuthentication().getPrincipal() instanceof User) {
            user = (User) authentication.getUserAuthentication().getPrincipal();
        } else {
            return accessToken;
        }
        DefaultOAuth2AccessToken oAuth2AccessToken = null;
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        } else {
            return accessToken;
        }
        Map<String, Object> userInfo = new HashMap<>(20);
        LoginUserInfo info = LoginUserInfo.getInstance(user);
        userInfo.put("userInfo", info);
        oAuth2AccessToken.setAdditionalInformation(userInfo);
        return oAuth2AccessToken;
    }
}
