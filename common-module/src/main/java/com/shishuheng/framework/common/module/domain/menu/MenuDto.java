package com.shishuheng.framework.common.module.domain.menu;

import com.shishuheng.framework.common.module.domain.base.BaseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 14:04:43
 */
@Data
@Api(tags = "菜单dto")
public class MenuDto extends BaseStatusDto {
    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "页面路径")
    private String path;

    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @ApiModelProperty(value = "层级 根级目录是0")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "拥有此菜单权限的角色")
    private Set<Long> roles;
}
