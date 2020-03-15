package com.shishuheng.framework.common.module.domain.role;

import com.shishuheng.framework.common.module.domain.base.BaseStatusEntity;
import com.shishuheng.framework.common.module.domain.menu.Menu;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/27 11:56 上午
 */
@Data
@Entity
@ApiModel(value = "角色")
public class Role extends BaseStatusEntity {
    @Column(name = "label")
    @ApiModelProperty(value = "角色标签")
    private String label;

    @Column(name = "code", unique = true)
    @ApiModelProperty(value = "角色代码")
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "权限集合")
    private Set<Permission> permissions;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "菜单集合")
    private Set<Menu> menus;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<User> users;
}
