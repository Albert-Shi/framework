package com.shishuheng.framework.authentication.service.base;

import com.shishuheng.framework.authentication.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author shishuheng
 * @date 2020/2/27 11:48 上午
 */
@Slf4j
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return validate(authentication, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return validate(authentication, permission);
    }

    /**
     * 具体的权限校验
     *
     * @param authentication
     * @param permission
     * @return
     */
    private boolean validate(Authentication authentication, Object permission) {
        if (null != authentication) {
            for (GrantedAuthority p : authentication.getAuthorities()) {
                if (SecurityUtil.authenticationJudge(p.getAuthority(), permission.toString())) {
                    return true;
                }
            }
            log.info("操作被禁止, 用户: {} 不包含操作权限: {}", authentication.getName(), permission.toString());
        } else {
            log.info("操作被禁止, 用户未登录");
        }
        return false;
    }
}
