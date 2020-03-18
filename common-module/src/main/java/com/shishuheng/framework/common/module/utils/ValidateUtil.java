package com.siwei.frame.car.order.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/3/16 4:39 下午
 */
public class ValidateUtil {
    private static final List<Validator> validatorList = new ArrayList<>();

    static {
        // 非空校验
        validatorList.add(new NotNullValidator());
        // 最大值校验
        validatorList.add(new MaxValidator());
        // 最小值校验
        validatorList.add(new MinValidator());
    }

    private ValidateUtil() {
    }

    /**
     * 使用默认校验组校验传入的实体类
     *
     * @param entity
     * @return
     */
    public static <T> String defaultValid(T entity) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(entity, Default.class);
        if (!validate.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            validate.forEach(e -> buffer.append(e.getMessage()).append(";"));
            return buffer.toString();
        }
        return null;
    }

    /**
     * 使用传入校验组(可以多个)校验传入的实体类
     *
     * @param entity
     * @return
     */
    public static <T> String validByGroups(T entity, Class<?>... groupsClass) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(entity, groupsClass);
        if (!validate.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            validate.forEach(e -> buffer.append(e.getMessage()).append(";"));
            return buffer.toString();
        }
        return null;
    }

    /**
     * 校验方法(不累计错误消息)
     *
     * @param message   消息收集map
     * @param obj       当前校验字段所属的对象
     * @param recursion 是否递归校验(即校验当前对象的字段的字段)
     * @param groups    校验组(若校验组为空则不做校验，但是若注解groups是空则全部校验)
     */
    public static void validate(Map<String, String> message, Object obj, boolean recursion, Class... groups) {
        validate(null, message, obj, recursion, false, groups);
    }

    /**
     * 校验方法
     *
     * @param message       消息收集map
     * @param obj           当前校验字段所属的对象
     * @param recursion     是否递归校验(即校验当前对象的字段的字段)
     * @param cumulateError 是否累计消息(否的话只记录最早发现的错误)
     * @param groups        校验组(若校验组为空则不做校验，但是若注解groups是空则全部校验)
     */
    public static void validate(Map<String, String> message, Object obj, boolean recursion, boolean cumulateError, Class... groups) {
        validate(null, message, obj, recursion, cumulateError, groups);
    }

    /**
     * 校验方法
     *
     * @param parentFiledName 上一级的字段名 并以.分割 形如 a.b
     * @param message         消息收集map
     * @param obj             当前校验字段所属的对象
     * @param recursion       是否递归校验(即校验当前对象的字段的字段)
     * @param cumulateError   是否累计消息(否的话只记录最早发现的错误)
     * @param groups          校验组
     */
    private static void validate(String parentFiledName, Map<String, String> message, Object obj, boolean recursion, boolean cumulateError, Class... groups) {
        if (null == obj) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean pass = true;
            Field field = fields[i];
            for (Validator validator : validatorList) {
                try {
                    pass = validator.validateMethod(parentFiledName, message, field, obj, groups);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (!message.isEmpty()) {
                    if (!cumulateError) {
                        return;
                    }
                }
            }
            if (recursion && pass) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    validate(field.getName(), message, value, recursion, cumulateError, groups);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /**
     * 添加校验器
     *
     * @param validator
     */
    public static void addValidator(Validator validator) {
        if (null != validator) {
            validatorList.add(validator);
        }
    }

    /**
     * 校验器抽象类 方便扩展
     */
    public static abstract class Validator {
        /**
         * 校验信息累计
         *
         * @param cumulative
         * @param single
         */
        public static void addMsg(Map<String, String> cumulative, Map<String, String> single) {
            if (null == single) {
                return;
            }
            if (null == cumulative) {
                cumulative = new HashMap<>();
            }
            // single实际上只会存在一组key-value
            for (Map.Entry<String, String> entry : single.entrySet()) {
                String val = cumulative.get(entry.getKey());
                if (null == val) {
                    cumulative.put(entry.getKey(), entry.getValue());
                } else {
                    val += " | " + entry.getValue();
                    cumulative.put(entry.getKey(), val);
                }
            }
        }

        /**
         * 校验方法
         *
         * @param field  校验字段
         * @param target 校验目标
         * @param groups 校验组
         * @return 返回是否校验通过 (只有真正校验通过了才返回true，不需要校验、校验失败等都返回false)
         */
        public abstract boolean validateMethod(String parentFiledName, Map<String, String> message, Field field, Object target, Class... groups) throws Exception;

        /**
         * 判断校验组是否符合
         *
         * @param groupsFromAnnotation
         * @param groupsFromParam
         * @return
         */
        public boolean shouldValidate(Class[] groupsFromAnnotation, Class[] groupsFromParam) {
            if (null == groupsFromAnnotation) {
                return true;
            }
            if (null == groupsFromParam) {
                return false;
            }
            List<Class> pList = Arrays.asList(groupsFromParam);
            for (int i = 0; i < groupsFromAnnotation.length; i++) {
                if (pList.contains(groupsFromAnnotation[i])) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 获得通用的字段名
         *
         * @param currentFieldName
         * @param parentFiledName
         * @return
         */
        public String commonFieldName(String currentFieldName, String parentFiledName) {
            String fieldName = currentFieldName;
            if (null != parentFiledName && !"".equals(parentFiledName)) {
                fieldName = parentFiledName + "." + currentFieldName;
            }
            return fieldName;
        }
    }

    /**
     * 非空校验实现
     */
    public static class NotNullValidator extends Validator {
        @Override
        public boolean validateMethod(String parentFiledName, Map<String, String> message, Field field, Object target, Class... groups) throws Exception {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (null != annotation) {
                boolean validate = shouldValidate(annotation.groups(), groups);
                if (!validate) {
                    return false;
                }
                // 实际的校验步骤
                field.setAccessible(true);
                Object value = field.get(target);
                if (null == value) {
                    String fieldName = commonFieldName(field.getName(), parentFiledName);
                    Map<String, String> error = new HashMap<>();
                    error.put(fieldName, annotation.message());
                    addMsg(message, error);
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 最大值校验
     */
    public static class MaxValidator extends Validator {
        @Override
        public boolean validateMethod(String parentFiledName, Map<String, String> message, Field field, Object target, Class... groups) throws Exception {
            Max annotation = field.getAnnotation(Max.class);
            if (null == annotation) {
                return false;
            }
            boolean validate = shouldValidate(annotation.groups(), groups);
            if (!validate) {
                return false;
            }
            field.setAccessible(true);
            Object value = field.get(target);
            if (null == value) {
                return false;
            }
            if (!(value instanceof Number)) {
                return false;
            }
            long max = annotation.value();
            Number n = (Number) value;
            if (n.doubleValue() > max) {
                String fieldName = commonFieldName(field.getName(), parentFiledName);
                Map<String, String> error = new HashMap<>();
                error.put(fieldName, annotation.message());
                addMsg(message, error);
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 最小值校验
     */
    public static class MinValidator extends Validator {
        @Override
        public boolean validateMethod(String parentFiledName, Map<String, String> message, Field field, Object target, Class... groups) throws Exception {
            Min annotation = field.getAnnotation(Min.class);
            if (null == annotation) {
                return false;
            }
            boolean validate = shouldValidate(annotation.groups(), groups);
            if (!validate) {
                return false;
            }
            field.setAccessible(true);
            Object value = field.get(target);
            if (null == value) {
                return false;
            }
            if (!(value instanceof Number)) {
                return false;
            }
            long min = annotation.value();
            Number n = (Number) value;
            if (n.doubleValue() < min) {
                String fieldName = commonFieldName(field.getName(), parentFiledName);
                Map<String, String> error = new HashMap<>();
                error.put(fieldName, annotation.message());
                addMsg(message, error);
                return false;
            } else {
                return true;
            }
        }
    }

    public static class SizeValidator extends Validator {
        @Override
        public boolean validateMethod(String parentFiledName, Map<String, String> message, Field field, Object target, Class... groups) throws Exception {
            Size annotation = field.getAnnotation(Size.class);
            if (null == annotation) {
                return false;
            }
            boolean validate = shouldValidate(annotation.groups(), groups);
            if (!validate) {
                return false;
            }
            field.setAccessible(true);
            Object value = field.get(target);
            if (null == value) {
                return false;
            }
            int min = annotation.min();
            int max = annotation.max();
            boolean failure = false;
            if (value instanceof CharSequence) {
                CharSequence cs = (CharSequence) value;
                if (cs.length() >= min && cs.length() <= max) {
                    return true;
                }
                failure = true;
            } else if (value instanceof Collection) {
                Collection collection = (Collection) value;
                if (collection.size() >= min && collection.size() <= max) {
                    return true;
                }
                failure = true;
            } else if (value instanceof Map) {
                Map map = (Map) value;
                if (map.size() >= min && map.size() <= max) {
                    return true;
                }
                failure = true;
            } else if (value.getClass().isArray()) {
                Object[] array = (Object[]) value;
                if (array.length >= min && array.length <= max) {
                    return true;
                }
                failure = true;
            }
            if (failure) {
                String fieldName = commonFieldName(field.getName(), parentFiledName);
                Map<String, String> error = new HashMap<>();
                error.put(fieldName, annotation.message());
                addMsg(message, error);
                return false;
            }
            return false;
        }
    }
}
