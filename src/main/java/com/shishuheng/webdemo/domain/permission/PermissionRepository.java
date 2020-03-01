package com.shishuheng.webdemo.domain.permission;

import com.shishuheng.webdemo.domain.base.BaseRepository;

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
