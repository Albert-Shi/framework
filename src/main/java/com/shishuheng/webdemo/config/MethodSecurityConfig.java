package com.shishuheng.webdemo.config;

import com.shishuheng.webdemo.service.base.MyPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020/2/27 4:14 下午
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private MyPermissionEvaluator permissionEvaluator;

    @Override
    public void setMethodSecurityExpressionHandler(List<MethodSecurityExpressionHandler> handlers) {
        super.setMethodSecurityExpressionHandler(handlers);
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        handlers.add(handler);
    }
}
