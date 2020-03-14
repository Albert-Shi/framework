package com.shishuheng.framework.common.module.service;


import com.shishuheng.framework.common.module.domain.base.BaseEntity;
import com.shishuheng.framework.common.module.domain.managed.ManagedEntity;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import com.shishuheng.framework.common.module.domain.status.Status;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.List;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/2/28 11:57 上午
 */
public abstract class AbstractBaseService<T extends BaseEntity> {
    @Value("${security.oauth2.client.client-id}")
    public String clientId;

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
        } else {
            findAllStatus();
        }
    }

    /**
     * 查询所有状态 并将状态存入map 方便快速使用
     */
    public abstract void findAllStatus();

    /**
     * 获取状态
     *
     * @param statusCode
     * @return
     */
    public abstract Status getStatus(String statusCode);

    public abstract boolean addManage();

    public abstract List<Status> initBaseStatus(String entityName, Set<Permission> permissions);

    public abstract ManagedEntity getManagedEntity();

    // @formatter:off
    // 以下方法抄自 csdn
    public T createModel() {
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

    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    private Class<?> getRawType(Type type) {
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
    // 摘录自 https://blog.csdn.net/tgbus18990140382/article/details/80622524
    // @formatter:on
}
