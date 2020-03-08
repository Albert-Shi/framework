package com.shishuheng.webdemo.domain.user;

import com.shishuheng.webdemo.domain.base.BaseStatusDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
public class UserDto extends BaseStatusDto {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色列表")
    private Set<Long> roleIds;
}
