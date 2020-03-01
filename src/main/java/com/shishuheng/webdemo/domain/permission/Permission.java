package com.shishuheng.webdemo.domain.permission;

import com.shishuheng.webdemo.domain.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author shishuheng
 * @date 2020/2/27 11:58 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ApiModel(value = "权限")
public class Permission extends BaseEntity implements GrantedAuthority {
    @ApiModelProperty(value = "权限标签")
    @Column(name = "label", unique = true)
    private String label;

    @ApiModelProperty(value = "权限值")
    @Column(name = "permission", unique = true)
    private String permission;

    @ApiModelProperty(value = "权限描述")
    @Column(name = "description")
    private String description;

//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "permissions")
//    private Set<Role> roles;

    public Permission(String label, String permission, String description) {
        this.label = label;
        this.permission = permission;
        this.description = description;
        this.setStatus(1);
        this.setCreatedDate(new Date());
    }

    public Permission() {
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
