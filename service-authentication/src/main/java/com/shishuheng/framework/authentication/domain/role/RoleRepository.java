package com.shishuheng.framework.authentication.domain.role;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.role.Role;

/**
 * @author shishuheng
 * @date 2020/2/27 12:38 下午
 */
public interface RoleRepository extends BaseRepository<Role> {
    /**
     * 根据标签查询角色
     *
     * @param label
     * @return
     */
    Role findRoleByLabel(String label);
}
