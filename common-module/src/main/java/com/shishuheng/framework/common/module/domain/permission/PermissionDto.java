package com.shishuheng.framework.common.module.domain.permission;

import com.shishuheng.framework.common.module.domain.base.BaseStatusDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shishuheng
 * @date 2020/2/27 11:58 上午
 */
@Data
@ApiModel(value = "权限dto")
public class PermissionDto extends BaseStatusDto {
    @ApiModelProperty(value = "权限标签")
    private String label;

    @ApiModelProperty(value = "权限值")
    private String permission;

    @ApiModelProperty(value = "权限描述")
    private String description;
}
