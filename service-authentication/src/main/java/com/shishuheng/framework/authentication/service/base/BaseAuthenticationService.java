package com.shishuheng.framework.authentication.service.base;


import com.shishuheng.framework.authentication.domain.entity.ManagedEntityRepository;
import com.shishuheng.framework.authentication.domain.status.StatusRepository;
import com.shishuheng.framework.authentication.helper.common.GlobalProperties;
import com.shishuheng.framework.common.module.domain.base.BaseEntity;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.status.Status;
import com.shishuheng.framework.common.module.service.AbstractBaseService;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/28 11:57 上午
 */
public abstract class BaseAuthenticationService<T extends BaseEntity> extends AbstractBaseService<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseAuthenticationService.class);

    @Autowired
    private ManagedEntityRepository managedEntityRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private GlobalProperties globalProperties;

    private ManagedEntity managedEntity;

    /**
     * 已存在的状态集合
     */
    private Map<String, Status> statusMap = new HashMap<>();

    /**
     * 查询所有状态 并将状态存入map 方便快速使用
     */
    @Override
    public void findAllStatus() {
        if (null != globalProperties.getLowMemoryMode() && globalProperties.getLowMemoryMode()) {
            // 若使用低内存模式 则不将状态缓存到内存map中
            logger.info("低内存模式将不缓存" + managedEntity.getClassName() + "的所有状态");
            return;
        }
        List<Status> statuses = statusRepository.findStatusesByEffectEntity(managedEntity);
        if (null != statuses && statuses.size() > 0) {
            for (Status s : statuses) {
                statusMap.put(s.getCode(), s);
            }
        }
    }

    /**
     * 获取状态
     *
     * @param statusCode
     * @return
     */
    @Override
    public Status getStatus(String statusCode) {
        Status status = null;
        // 若未使用低内存模式 则直接从内存map取
        if (null != globalProperties.getLowMemoryMode() && !globalProperties.getLowMemoryMode()) {
            status = statusMap.get(statusCode);
            if (null == status) {
                // 若map不存在此状态 则再从数据库取
                status = statusRepository.findStatusByCodeAndEffectEntity(statusCode, managedEntity);
            }
            if (null != status) {
                // 若从数据库取出此状态 则直接存入map 方便下次使用
                statusMap.put(status.getCode(), status);
            }
        } else {
            // 若使用低内存模式 则直接去数据库取
            status = statusRepository.findStatusByCodeAndEffectEntity(statusCode, managedEntity);
        }
        return status;
    }

    @Override
    public boolean addManage() {
        T entity = createModel();
        if (null == entity) {
            logger.info("实例化对象失败");
            return false;
        }
        String className = entity.getClass().getSimpleName();
        ManagedEntity one = managedEntityRepository.findManagedEntityByClassName(className);
        if (null == one) {
            ManagedEntity managedEntity = new ManagedEntity();
            managedEntity.setClassName(entity.getClass().getSimpleName());
            managedEntity.setLabel(entity.getClass().getAnnotation(ApiModel.class).value());
            managedEntity.setFromServiceClient(clientId);
            managedEntity.setCreatedDate(new Date());
            managedEntityRepository.save(managedEntity);
            this.managedEntity = managedEntity;
            logger.info("将实体: {} 加入管理", className);
            return true;
        } else {
            this.managedEntity = one;
            return false;
        }
    }

    @Override
    public List<Status> initBaseStatus(String entityName, Set<Permission> permissions) {
        if (null != permissions && permissions.size() < 1) {
            permissions = null;
        }
        if (null == entityName) {
            entityName = managedEntity.getLabel();
        }
        Status enabled = new Status(managedEntity.getClassName() + "Enabled", managedEntity.getLabel() + "启用", entityName + "实体处于启用的状态,可以使用绝大部分权限", managedEntity, permissions);
        Status disabled = new Status(managedEntity.getClassName() + "Disabled", managedEntity.getLabel() + "禁用", entityName + "实体处于禁用的状态,权限会受到限制", managedEntity, permissions);
        List<Status> statuses = Arrays.asList(enabled, disabled);
        statusRepository.saveAll(statuses);
        return statuses;
    }

    @Override
    public ManagedEntity getManagedEntity() {
        return this.managedEntity;
    }
}
