package com.shishuheng.framework.authentication.domain.entity;

import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;

/**
 * @author shishuheng
 * @date 2020/3/3 11:03 上午
 */
public interface ManagedEntityRepository extends BaseRepository<ManagedEntity> {
    /**
     * 根据类名查询
     *
     * @param className
     * @return
     */
    ManagedEntity findManagedEntityByClassName(String className);
}
