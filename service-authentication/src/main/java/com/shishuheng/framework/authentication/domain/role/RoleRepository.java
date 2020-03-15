package com.shishuheng.framework.authentication.domain.role;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.role.Role;

import java.util.Collection;
import java.util.List;

/**
 * @author shishuheng
 * @date 2020/2/27 12:38 下午
 */
public interface RoleRepository extends BaseRepository<Role> {
    /**
     * 根据代码查询角色
     *
     * @param code
     * @return
     */
    Role findRoleByCode(String code);

    /**
     * 根据id查询角色
     *
     * @param ids
     * @return
     */
    List<Role> findRolesByIdIn(Collection<Long> ids);
}
