package com.shishuheng.framework.authentication.domain.status;


import com.shishuheng.framework.common.module.domain.base.BaseRepository;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import com.shishuheng.framework.common.module.domain.status.Status;
import org.springframework.data.jpa.repository.Query;

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
     * 根据状态代码和所属实体类型id查询
     *
     * @param code
     * @param managedEntityId
     * @return
     */
    @Query(value = "select s from Status as s where s.code = ?1 and s.effectEntity.id = ?2")
    Status findStatusByCodeAndEffectEntityId(String code, Long managedEntityId);

    @Query(value = "select s from Status as s where s.effectEntity.id = ?1")
    List<Status> findStatusesByEffectEntityId(Long managedEntityId);

    /**
     * 根据所属实体类型查询
     *
     * @param effectEntity
     * @return
     */
    List<Status> findStatusesByEffectEntity(ManagedEntity effectEntity);
}
