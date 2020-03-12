package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.service.StatusService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.status.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020年 03月 11日 23:01:37
 */
@Api(tags = "状态管理")
@RestController
@RequestMapping(value = "/status")
public class StatusController {
    @Autowired
    private StatusService service;

    @PostMapping(name = "/findStatusByCodeAndEffectEntityId")
    @ApiOperation(httpMethod = "POST", value = "根据状态码和类型实体id查询状态")
    public Status findStatusByCodeAndEffectEntityId(@RequestParam(value = "code") String code, @RequestParam(value = "managedEntityId") Long managedEntityId) {
        return service.findStatusByCodeAndEffectEntityId(code, managedEntityId);
    }

    @PostMapping(value = "/findStatusesByEffectEntityId")
    public List<Status> findStatusesByEffectEntityId(@RequestParam(value = "managedEntityId") Long managedEntityId) {
        return service.findStatusesByEffectEntityId(managedEntityId);
    }

    @PostMapping(value = "/addAllStatus")
    public Result<String> addAllStatus(@RequestBody List<Status> statuses) {
        return service.addAllStatus(statuses);
    }
}
