package com.shishuheng.webdemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shishuheng
 * @date 2020年 03月 02日 20:10:46
 */
public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 校验的请求路径
     */
    private static final String LOGIN_PATH = "/myLogin";
    /**
     * 允许请求的方式
     */
    private static final String[] REQUEST_METHODS = {"POST"};
    /**
     * 属性值的key
     */
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    public MyAuthenticationFilter() {
        super(LOGIN_PATH);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (checkMethod(httpServletRequest)) {
            return this.getAuthenticationManager().authenticate(buildAuthentication(httpServletRequest));
        } else {
            return null;
        }
    }

    private boolean checkMethod(HttpServletRequest request) {
        String method = request.getMethod();
        StringBuffer info = new StringBuffer();
        for (int i = 0; i < REQUEST_METHODS.length; i++) {
            String m = REQUEST_METHODS[i];
            info.append(m).append(",");
            if (m.equals(method)) {
                return true;
            }
        }
        info.delete(info.length() - 1, 0);
        logger.info("登录只允许" + info.toString());
        return false;
    }

    private Authentication buildAuthentication(HttpServletRequest request) {
        String username = null == request.getParameter(USERNAME_KEY) ? "username" : request.getParameter(USERNAME_KEY);
        String password = null == request.getParameter(PASSWORD_KEY) ? "password" : request.getParameter(PASSWORD_KEY);
        MyAuthenticationToken authenticationToken = new MyAuthenticationToken(null, username, password);
        return authenticationToken;
    }
}
