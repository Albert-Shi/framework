package com.shishuheng.webdemo.controller;

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
@RequestMapping(value = "/test")
public class TestController {
    @GetMapping(value = "/echo")
    @PreAuthorize(value = "hasPermission(null, '/echo')")
    public String test(@RequestParam(name = "value", required = false, defaultValue = "test") String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String res = "现在时间是:" + dateFormat.format(new Date()) + ", value = " + value;
        return res;
    }
}
