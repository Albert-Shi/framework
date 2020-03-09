package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.domain.base.Result;
import com.shishuheng.framework.authentication.domain.role.Role;
import com.shishuheng.framework.authentication.domain.role.RoleDto;
import com.shishuheng.framework.authentication.service.RoleService;
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
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService service;

    @PostMapping("/add")
    @ApiOperation(value = "新增角色", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/role/add')")
    public Result<String> addRole(@RequestBody @Validated RoleDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        }
        return service.addRole(dto);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询角色列表", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/role/find')")
    public Result<List<Role>> findList(@RequestBody(required = false) RoleDto dto) {
        return service.findRoleList(dto);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑角色", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/role/edit')")
    public Result<String> update(@RequestBody @Validated RoleDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        } else {
            return service.updateRole(dto);
        }
    }
}
