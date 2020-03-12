package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.service.ManagedEntityService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shishuheng
 * @date 2020年 03月 12日 00:00:39
 */
@RestController
@RequestMapping("/managed-entity")
public class ManagedEntityController {
    @Autowired
    private ManagedEntityService service;

    @PostMapping(value = "/findManagedEntityByClassName")
    public ManagedEntity findManagedEntityByClassName(@RequestParam(value = "className") String className) {
        return service.findManagedEntityByClassName(className);
    }

    @PostMapping(value = "/addManagedEntity")
    public Result<ManagedEntity> addManagedEntity(@RequestBody ManagedEntity managedEntity) {
        return service.addOne(managedEntity);
    }
}
