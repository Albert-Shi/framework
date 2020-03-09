package com.shishuheng.framework.authentication.service.base;

import com.alibaba.fastjson.JSON;
import com.shishuheng.framework.authentication.domain.base.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shishuheng
 * @date 2020/2/28 9:38 上午
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)
            throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf8");
        httpServletResponse.setContentType("application/json");
        Result result = new Result(Result.Exceptions.PERMISSION_DENIED);
        String jsonStr = JSON.toJSONString(result);
        httpServletResponse.getWriter().println(jsonStr);
        httpServletResponse.getWriter().flush();
    }
}
