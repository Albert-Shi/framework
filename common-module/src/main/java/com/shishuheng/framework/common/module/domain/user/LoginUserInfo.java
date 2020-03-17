package com.shishuheng.framework.common.module.domain.user;

import com.shishuheng.framework.common.module.domain.menu.Menu;
import com.shishuheng.framework.common.module.domain.menu.SimpleMenuVo;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.role.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 20:29:04
 */
@Data
public class LoginUserInfo {
    private String username;
    private String label;
    private Set<String> permissions;
    private Set<String> roles;
    private Set<SimpleMenuVo> menus;

    public static LoginUserInfo getInstance(User user) {
        if (null == user) {
            return null;
        }
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        Set<Menu> menus = new HashSet<>();
        user.getAuthorities().forEach(e -> permissions.add(e.getAuthority()));
        for (Role role : user.getRoles()) {
            roles.add(role.getCode());
            role.getMenus().forEach(e -> menus.add(e));
        }

        LoginUserInfo info = new LoginUserInfo();
        info.setUsername(user.getUsername());
        info.setLabel(user.getLabel());
        info.setRoles(roles);
        info.setPermissions(permissions);
        info.setMenus(SimpleMenuVo.createMenuTree(menus));
        return info;
    }
}
