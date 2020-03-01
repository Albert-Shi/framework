package com.shishuheng.webdemo.domain.role;

import com.shishuheng.webdemo.domain.base.BaseRepository;

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
