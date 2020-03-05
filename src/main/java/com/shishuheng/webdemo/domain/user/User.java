package com.shishuheng.webdemo.domain.user;

import com.shishuheng.webdemo.domain.base.BaseEntity;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.role.Role;
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
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username", unique = true, nullable = false)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "密码")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "角色列表")
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> permissions = new HashSet<>();
        for (Role role : roles) {
            permissions.addAll(role.getPermissions());
        }
        return permissions;
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
