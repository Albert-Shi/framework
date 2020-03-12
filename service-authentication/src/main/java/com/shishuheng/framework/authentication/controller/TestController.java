package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.authentication.helper.common.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shishuheng
 * @date 2020/2/27 3:55 下午
 */
@RestController
@RefreshScope
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private GlobalProperties globalProperties;

    @Value("${global.lowMemoryMode}")
    private String low;

    @GetMapping(value = "/echo")
    @PreAuthorize(value = "hasPermission(null, '/echo')")
    public String test(@RequestParam(name = "value", required = false, defaultValue = "test") String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String res = "现在时间是:" + dateFormat.format(new Date()) + ", value = " + value;
        return res;
    }

    @GetMapping("/prop")
    public Result properties() {
        return new Result(globalProperties.getLowMemoryMode() + " " + low);
    }
}
