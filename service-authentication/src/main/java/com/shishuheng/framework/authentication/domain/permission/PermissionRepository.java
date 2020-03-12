package com.shishuheng.framework.authentication.domain.permission;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.permission.Permission;

/**
 * @author shishuheng
 * @date 2020/2/27 12:38 下午
 */
public interface PermissionRepository extends BaseRepository<Permission> {
    /**
     * 根据标签查询权限
     *
     * @param label
     * @return
     */
    Permission findPermissionByLabel(String label);
}
