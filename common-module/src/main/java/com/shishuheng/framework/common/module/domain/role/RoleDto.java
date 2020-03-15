package com.shishuheng.framework.common.module.domain.role;

import com.shishuheng.framework.common.module.domain.base.BaseStatusDto;
import com.shishuheng.framework.common.module.utils.group.AddValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/27 11:56 上午
 */
@Data
@ApiModel(value = "角色Dto")
public class RoleDto extends BaseStatusDto {
    @NotBlank(message = "角色标签不能为空", groups = {AddValidationGroup.class})
    @ApiModelProperty(value = "角色标签")
    private String label;

    @NotBlank(message = "角色代码不能为空", groups = {AddValidationGroup.class})
    @ApiModelProperty(value = "角色代码")
    private String code;

    @ApiModelProperty(value = "权限集合")
    private Set<Long> permissionIds;

    @ApiModelProperty(value = "菜单集合")
    private Set<Long> menuIds;
}
