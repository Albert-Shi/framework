package com.shishuheng.webdemo.service.base;

import com.shishuheng.webdemo.domain.base.BaseEntity;
import com.shishuheng.webdemo.domain.entity.ManagedEntity;
import com.shishuheng.webdemo.domain.entity.ManagedEntityRepository;
import com.shishuheng.webdemo.domain.permission.Permission;
import com.shishuheng.webdemo.domain.status.Status;
import com.shishuheng.webdemo.domain.status.StatusRepository;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private ManagedEntity managedEntity;

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

    @PostConstruct
    public void init() {
        boolean needInit = addManage();
        if (needInit) {
            Set<Permission> permissions = initPermission();
            if (null != managedEntity && !managedEntity.getClassName().equalsIgnoreCase("Status")) {
                initBaseStatus(null, permissions);
            }
            initStatus();
            initEntity();
        }
    }

    private boolean addManage() {
        T entity = createModel();
        if (null == entity) {
            logger.info("实例化对象失败");
            return false;
        }
        if (entity instanceof ManagedEntity) {
            logger.info("ManagedEntity的addManage()的不执行跳过");
            return true;
        }
        String className = entity.getClass().getSimpleName();
        ManagedEntity one = managedEntityRepository.findManagedEntityByClassName(className);
        if (null == one) {
            if (className.equalsIgnoreCase("Status")) {
                initBaseStatus("状态", null);
            }
            ManagedEntity managedEntity = new ManagedEntity();
            managedEntity.setClassName(entity.getClass().getSimpleName());
            managedEntity.setLabel(entity.getClass().getAnnotation(ApiModel.class).value());
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
        Status enabled = new Status("enabled", "启用", entityName + "实体处于启用的状态,可以使用绝大部分权限", managedEntity, permissions);
        Status disabled = new Status("disabled", "禁用", entityName + "实体处于禁用的状态,权限会受到限制", managedEntity, permissions);
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
