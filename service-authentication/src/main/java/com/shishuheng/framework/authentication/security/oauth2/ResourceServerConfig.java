package com.shishuheng.framework.authentication.security.oauth2;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shishuheng
 * @date 2020/3/4 5:13 下午
 */
@Component
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String[] EMPTY = {};

    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> swagger = Arrays.asList("/swagger-ui.html", "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**", "/v2/api-docs/**");
        String[] paths = combine(swagger);
        http.csrf().disable();
        http.authorizeRequests().antMatchers(paths).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
    }

    private String[] combine(List<String>... pathList) {
        List<String> combined = new ArrayList<>();
        if (null == pathList || pathList.length < 1) {
            return EMPTY;
        }
        for (int i = 0; i < pathList.length; i++) {
            combined.addAll(pathList[i]);
        }
        return combined.toArray(new String[combined.size()]);
    }
}
