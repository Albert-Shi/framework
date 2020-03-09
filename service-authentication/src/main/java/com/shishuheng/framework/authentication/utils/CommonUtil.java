package com.shishuheng.framework.authentication.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author shishuheng
 * @date 2020/2/28 11:36 上午
 */
public class CommonUtil {
    /**
     * 获取校验失败信息
     *
     * @param bindingResult
     * @return
     */
    public static String getValidateErrorMessage(BindingResult bindingResult) {
        if (null == bindingResult || !bindingResult.hasErrors()) {
            return null;
        }
        StringBuffer err = new StringBuffer();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError e : fieldErrors) {
            err.append(e.getDefaultMessage()).append(", ");
        }
        // 删除最后的 ', '
        err.delete(err.length() - 2, err.length());
        return err.toString();
    }

    /**
     * 二次封装属性复制方法
     *
     * @param source           源对象
     * @param target           目标对象
     * @param copyNull         是否复制值为null的属性
     * @param ignoreProperties 忽略的属性
     */
    public static void copyObject(Object source, Object target, boolean copyNull, String... ignoreProperties) {
        if (copyNull) {
            if (null != ignoreProperties && ignoreProperties.length > 0) {
                BeanUtils.copyProperties(source, target, ignoreProperties);
            } else {
                BeanUtils.copyProperties(source, target);
            }
        } else {
            List<String> list = null;
            if (null != ignoreProperties) {
                list = Arrays.asList(ignoreProperties);
            } else {
                list = new ArrayList<>();
            }
            try {
                Field[] fields = source.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    Object val = field.get(source);
                    if (null == val) {
                        list.add(field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Set<String> removeSame = new HashSet<>(list);
            String[] omit = removeSame.toArray(new String[removeSame.size()]);
            BeanUtils.copyProperties(source, target, omit);
        }
    }
}
