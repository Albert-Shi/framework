package com.shishuheng.framework.common.module.feign;

import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import com.shishuheng.framework.common.module.domain.status.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020年 03月 11日 23:35:24
 */
@Slf4j
public class AuthenticationFeignFallback implements AuthenticationFeign {
    @Override
    public Status findStatusByCodeAndEffectEntityId(String code, Long managedEntityId) {
        log.error("feign调用 findStatusByCodeAndEffectEntityId 失败");
        return null;
    }

    @Override
    public List<Status> findStatusesByEffectEntityId(Long managedEntityId) {
        log.error("feign调用 findStatusesByEffectEntityId 失败");
        return null;
    }

    @Override
    public Result<String> addAllStatus(List<Status> statuses) {
        log.error("feign调用 addAllStatus 失败");
        return null;
    }

    @Override
    public ManagedEntity findManagedEntityByClassName(String className) {
        log.error("feign调用 findManagedEntityByClassName 失败");
        return null;
    }

    @Override
    public Result<ManagedEntity> addManagedEntity(ManagedEntity managedEntity) {
        log.error("feign调用 addManagedEntity 失败");
        return null;
    }

    @Override
    public Boolean getLowMemoryMode() {
        log.error("feign调用 getLowMemoryMode 失败");
        return null;
    }
}
