package com.shishuheng.framework.authentication.controller;

import com.shishuheng.framework.authentication.service.MenuService;
import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.menu.Menu;
import com.shishuheng.framework.common.module.domain.menu.MenuDto;
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
 * @date 2020年 03月 15日 15:47:46
 */
@RestController
@RequestMapping(value = "/menu")
@ApiOperation(value = "菜单管理")
public class MenuController {
    @Autowired
    private MenuService service;

    @PostMapping("/add")
    @ApiOperation(value = "新增菜单", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/menu/add')")
    public Result<String> addMenu(@RequestBody @Validated MenuDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        }
        return service.addMenu(dto);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询菜单列表", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/menu/find')")
    public Result<List<Menu>> findList(@RequestBody(required = false) MenuDto dto) {
        return service.findByConditions(dto);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑权限", httpMethod = "POST")
    @PreAuthorize(value = "hasPermission(null, '/menu/edit')")
    public Result<String> update(@RequestBody @Validated MenuDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(bindingResult);
        } else {
            return service.updateMenu(dto);
        }
    }
}
