package com.shishuheng.framework.common.module;

import java.util.Base64;

/**
 * @author shishuheng
 * @date 2020/3/26 1:48 下午
 */
public class CommonModuleApplication {
    public static void main(String[] args) throws  Exception {
        // nothing to do but offer a main method for spring-boot-maven-plugin to package
        String a = "client-authentication:abcdef";
        byte[] bytes = Base64.getEncoder().encode(a.getBytes("utf8"));
        String result = new String(bytes);
        System.out.println(result);
    }
}
