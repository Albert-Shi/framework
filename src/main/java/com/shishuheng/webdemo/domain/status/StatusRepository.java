package com.shishuheng.webdemo.domain.status;

import com.shishuheng.webdemo.domain.base.BaseRepository;
import com.shishuheng.webdemo.domain.entity.ManagedEntity;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020/3/3 10:14 上午
 */
public interface StatusRepository extends BaseRepository<Status> {
    /**
     * 根据状态代码和所属实体类型查询
     *
     * @param code
     * @param managedEntity
     * @return
     */
    Status findStatusByCodeAndEffectEntity(String code, ManagedEntity managedEntity);

    /**
     * 根据所属实体类型查询
     *
     * @param effectEntity
     * @return
     */
    List<Status> findStatusesByEffectEntity(ManagedEntity effectEntity);
}
