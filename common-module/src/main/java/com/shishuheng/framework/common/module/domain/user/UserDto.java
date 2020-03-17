package com.shishuheng.framework.common.module.domain.user;

import com.shishuheng.framework.common.module.domain.base.BaseStatusDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@ApiModel(value = "用户信息dto")
public class UserDto extends BaseStatusDto {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户标签")
    private String label;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色列表")
    private Set<Long> roleIds;
}
