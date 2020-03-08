package com.shishuheng.webdemo.domain.role;

import com.shishuheng.webdemo.domain.base.BaseStatusEntity;
import com.shishuheng.webdemo.domain.permission.Permission;
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
    @Column(name = "label", unique = true)
    @ApiModelProperty(value = "角色标签")
    private String label;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "权限集合")
    private Set<Permission> permissions;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<User> users;
}
