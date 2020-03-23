package com.shishuheng.framework.common.module.domain.user;

import com.shishuheng.framework.common.module.domain.base.BaseStatusEntity;
import com.shishuheng.framework.common.module.domain.department.Department;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.role.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@Entity
@ApiModel(value = "用户")
public class User extends BaseStatusEntity implements UserDetails {
    @Column(name = "username", unique = true, nullable = false)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "label")
    @ApiModelProperty(value = "用户标签")
    private String label;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "密码")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "角色列表")
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "部门列表")
    private Set<Department> departments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 用户所属角色包含的权限
        Set<Permission> rolePermissions = new HashSet<>();
        for (Role role : roles) {
            rolePermissions.addAll(role.getPermissions());
        }
        // 用户所属部门包含的权限
        Set<Permission> departmentPermissions = new HashSet<>();
        for (Department department : departments) {
            departmentPermissions.addAll(department.getPermissions());
        }
        // 二者取交集
        Set<Permission> result = new HashSet<>(rolePermissions);
        result.retainAll(departmentPermissions);
        return result;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus().getCode().contains("UserEnabled");
    }
}
