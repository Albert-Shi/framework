package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.helper.common.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shishuheng
 * @date 2020年 03月 11日 23:41:46
 */
@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private GlobalProperties globalProperties;

    @PostMapping("/getLowMemoryMode")
    public Boolean getLowMemoryMode() {
        return globalProperties.getLowMemoryMode();
    }
}
