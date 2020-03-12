package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.authentication.service.PermissionService;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.permission.PermissionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020/2/28 10:20 上午
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService service;

    @PostMapping("/add")
    @ApiOperation(value = "新增权限", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/permission/add')")
    public Result<String> addRole(@RequestBody @Validated PermissionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        }
        return service.addRole(dto);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询权限列表", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/permission/find')")
    public Result<List<Permission>> findList(@RequestBody(required = false) PermissionDto dto) {
        return service.findPermissionList(dto);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑权限", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/permission/edit')")
    public Result<String> update(@RequestBody @Validated PermissionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        } else {
            return service.updatePermission(dto);
        }
    }
}
