package com.shishuheng.webdemo.domain.entity;

import com.shishuheng.webdemo.domain.base.BaseRepository;

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
