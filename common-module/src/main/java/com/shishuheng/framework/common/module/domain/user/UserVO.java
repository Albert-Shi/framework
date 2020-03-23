package com.shishuheng.framework.common.module.domain.user;

import com.shishuheng.framework.common.module.domain.base.BaseStatusEntity;
import com.shishuheng.framework.common.module.domain.department.Department;
import com.shishuheng.framework.common.module.domain.role.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@ApiModel(value = "用户")
public class UserVO extends BaseStatusEntity {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户标签")
    private String label;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色列表")
    private Set<Role> roles;

    @ApiModelProperty(value = "部门列表")
    private Set<Department> departments;
}
