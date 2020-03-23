package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.service.UserService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.user.UserDto;
import com.shishuheng.framework.common.module.domain.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020/2/28 10:20 上午
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping(value = "/find/{id}")
    @ApiOperation(value = "根据id查询", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/user/find')")
    public Result<UserVO> findUserById(@PathVariable(name = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/user/add')")
    public Result<String> addRole(@RequestBody @Validated UserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        }
        return service.addUser(dto);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询用户列表", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/user/list')")
    public Result<List<UserDto>> findList(@RequestBody(required = false) UserDto dto) {
        return service.findUserList(dto);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑用户", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/user/edit')")
    public Result<String> update(@RequestBody @Validated UserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        } else {
            return service.updateRole(dto);
        }
    }
}
