package com.shishuheng.framework.common.module.feign;

import com.shishuheng.framework.common.module.domain.base.Result;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import com.shishuheng.framework.common.module.domain.status.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author shishuheng
 * @date 2020年 03月 11日 22:58:59
 */
@FeignClient(value = "service-authentication", fallback = AuthenticationFeignFallback.class)
public interface AuthenticationFeign {
    @PostMapping(value = "/status/findStatusByCodeAndEffectEntityId")
    Status findStatusByCodeAndEffectEntityId(@RequestParam(value = "code") String code,
                                             @RequestParam(value = "managedEntityId") Long managedEntityId);

    @PostMapping(value = "/status/findStatusesByEffectEntityId")
    List<Status> findStatusesByEffectEntityId(@RequestParam(value = "managedEntityId") Long managedEntityId);

    @PostMapping(value = "/status/addAllStatus")
    Result<String> addAllStatus(@RequestBody List<Status> statuses);

    @PostMapping("/property/getLowMemoryMode")
    Boolean getLowMemoryMode();

    @PostMapping(value = "/managed-entity/findManagedEntityByClassName")
    ManagedEntity findManagedEntityByClassName(@RequestParam(value = "className") String className);

    @PostMapping(value = "/managed-entity/addManagedEntity")
    Result<ManagedEntity> addManagedEntity(@RequestBody ManagedEntity managedEntity);
}
