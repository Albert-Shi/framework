package com.shishuheng.framework.common.module.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author shishuheng
 * @date 2020年3月1日 1点25分
 */
public class SecurityUtil {
    /**
     * 校验
     *
     * @param pattern 匹配模板字符
     * @param url     匹配字符
     * @return
     */
    public static boolean authenticationJudge(String pattern, String url) {
        if (null == pattern || null == url) {
            return false;
        }
        // 规范化匹配模板
        pattern = patternTrim(pattern);
        String[] patternArray = pattern.split("/");
        String[] urlArray = url.split("/");

        // 去除通配符后 看剩余的模板字符是否全部存在于要匹配的字符中
        // 若非全部存在 则直接返回false
        String[] nonWildcard = pattern.replace("/**", "").replace("/*", "").split("/");
        List<String> urlList = Arrays.asList(urlArray);
        List<String> nonWildcardList = Arrays.asList(nonWildcard);
        if (!urlList.containsAll(nonWildcardList)) {
            return false;
        }

        // 当模板不是以通配符开始 则先比较起始部分是否相同 若不同则返回false
        if (!"*".equals(patternArray[0])
                && !"**".equals(patternArray[0])
                && !patternArray[0].equals(urlArray[0])) {
            return false;
        }
        // 当模板不是以通配符结束 则比较结束部分是否相同 若不同则返回false
        boolean endNotWithWildcard = false;
        if (!"*".equals(patternArray[patternArray.length - 1])
                && !"**".equals(patternArray[patternArray.length - 1])) {
            if (!patternArray[patternArray.length - 1].equals(urlArray[urlArray.length - 1])) {
                return false;
            } else {
                // 不以通配符结尾
                endNotWithWildcard = true;
            }
        }
        int i = 0;
        int j = 0;
        int[] step = {0, 0, 0};
        while (i < patternArray.length && j < urlArray.length) {
            String p1 = patternArray[i];
            String p2 = null;
            if (i + 1 < patternArray.length) {
                p2 = patternArray[i + 1];
            }
            step = match(p1, p2, urlArray[j]);
            if (step[0] == 0 && step[1] == 0) {
                return false;
            }
            i += step[0];
            j += step[1];
        }
        // 若模板的最后一位不是通配符 /* 或者 /** 则判断匹配字符是否比较到最后一位 若是则返回true 否则返回false
        // 解决形如 模板: /*/*/c 匹配字符: /b/c 出错的情况
        if (endNotWithWildcard && step[2] == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 规范化匹配模板
     *
     * @param pattern
     * @return
     */
    private static String patternTrim(String pattern) {
        // 把形如 /**/* 或者 /*/** 以及 /**/** 统统转换为 /**
        String type1 = "/**/*";
        String type2 = "/*/**";
        String type0 = "/**/**";
        String standard = "/**";
        while (pattern.contains(type0) || pattern.contains(type1) || pattern.contains(type2)) {
            if (pattern.contains(type0)) {
                pattern = pattern.replace(type0, standard);
                continue;
            }
            if (pattern.contains(type1)) {
                pattern = pattern.replace(type1, standard);
                continue;
            }
            if (pattern.contains(type2)) {
                pattern = pattern.replace(type2, standard);
                continue;
            }
        }
        return pattern;
    }

    /**
     * 比较方法
     *
     * @param p1 当前匹配模板
     * @param p2 当前匹配模板后面的一个模板
     * @param u  当前匹配字符
     * @return step[3]一维数组 step[0]是匹配模板后移步数 step[1]是匹配字符后移步数 step[2]表示是匹配类型 0以*匹配 1以**匹配 2以文字匹配
     */
    private static int[] match(String p1, String p2, String u) {
        int[] step = {0, 0, 0};
        if ("*".equals(p1)) {
            step[0] = 1;
            step[1] = 1;
            step[2] = 0;
        } else if ("**".equals(p1)) {
            if (null != p2 && p2.equals(u)) {
                step[0] = 2;
            }
            step[1] = 1;
            step[2] = 1;
        } else {
            if (p1.equals(u)) {
                step[0] = 1;
                step[1] = 1;
                step[2] = 2;
            }
        }
        return step;
    }

    public static void main(String[] args) {
        String a = "/**/*/456/*/789";
        String b = "/123/bcd/456/abc/789";
        boolean pass = authenticationJudge(a, b);
        System.out.println(pass);
    }
}
