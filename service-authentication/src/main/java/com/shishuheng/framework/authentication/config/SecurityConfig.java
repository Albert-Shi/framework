package com.shishuheng.framework.authentication.config;

import com.shishuheng.framework.authentication.service.UserService;
import com.shishuheng.framework.common.module.service.MyAccessDeniedHandler;
import com.shishuheng.framework.common.module.service.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author shishuheng
 * @date 2020/2/26 10:01 上午
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.formLogin();
        http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter();
//        myAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
//        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
//        http.authenticationProvider(myAuthenticationProvider).addFilterAfter(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
