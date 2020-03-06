package com.shishuheng.webdemo.service.base;

import com.shishuheng.webdemo.domain.base.BaseEntity;
import com.shishuheng.webdemo.domain.entity.ManagedEntity;
import com.shishuheng.webdemo.domain.entity.ManagedEntityRepository;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.status.Status;
import com.shishuheng.webdemo.domain.status.StatusRepository;
import com.shishuheng.webdemo.helper.common.GlobalProperties;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/28 11:57 上午
 */
public abstract class BaseService<T extends BaseEntity> {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

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

    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    /**
     * 初始化此实体的权限
     */
    public abstract Set<Permission> initPermission();

    /**
     * 初始化此实体的状态
     */
    public abstract void initStatus();

    /**
     * 初始化需必要实体
     */
    public abstract void initEntity();

    /**
     * Service初始化
     * <p>
     * 此处各个方法的调用顺序不可修改
     */
    @PostConstruct
    public void init() {
        boolean needInit = addManage();
        if (needInit) {
            Set<Permission> permissions = initPermission();
            initBaseStatus(null, permissions);
            initStatus();
            findAllStatus();
            initEntity();
        }
    }

    /**
     * 查询所有状态 并将状态存入map 方便快速使用
     */
    private void findAllStatus() {
        if (null != globalProperties.getLowMemoryMode() && globalProperties.getLowMemoryMode()) {
            // 若使用低内存模式 则不将状态缓存到内存map中
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

    private boolean addManage() {
        T entity = createModel();
        if (null == entity) {
            logger.info("实例化对象失败");
            return false;
        }
        String className = entity.getClass().getSimpleName();
        ManagedEntity one = managedEntityRepository.findManagedEntityByClassName(className);
        if (null == one) {
            ManagedEntity effectEntity = managedEntityRepository.findManagedEntityByClassName(ManagedEntity.class.getSimpleName());
            Status meEnabled = statusRepository.findStatusByCodeAndEffectEntity(ManagedEntity.class.getSimpleName() + "Enabled", effectEntity);
            ManagedEntity managedEntity = new ManagedEntity();
            managedEntity.setClassName(entity.getClass().getSimpleName());
            managedEntity.setLabel(entity.getClass().getAnnotation(ApiModel.class).value());
            managedEntity.setStatus(meEnabled);
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

    private List<Status> initBaseStatus(String entityName, Set<Permission> permissions) {
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

    public ManagedEntity getManagedEntity() {
        return this.managedEntity;
    }

    // 以下方法抄自 csdn
    private T createModel() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = getRawType(type);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 摘录自 https://blog.csdn.net/tgbus18990140382/article/details/80622524
}
