package com.gx.crowd.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 12:06
 */
public class ResourceAccess {
    private static Set<String> RESOURCE = new HashSet<>();
    private static Set<String> STATIC_RESOURCE = new HashSet<>();

    static {
        //非静态资源
        RESOURCE.add("/");
        RESOURCE.add("/auth/login/page");
        RESOURCE.add("/auth/reg/page");
        RESOURCE.add("/auth/do/logout");
        RESOURCE.add("/auth/do/reg");
        RESOURCE.add("/auth/short/message");
        //静态资源
        STATIC_RESOURCE.add("bootstrap");
        STATIC_RESOURCE.add("css");
        STATIC_RESOURCE.add("fonts");
        STATIC_RESOURCE.add("jquery");
        STATIC_RESOURCE.add("img");
        STATIC_RESOURCE.add("script");
        STATIC_RESOURCE.add("ztree");
    }

    /**
     * 检查资源
     *
     * @return
     */
    public static boolean checkResources(String path) {
        //判断是否存在RESOURCE
        if (!RESOURCE.contains(path)) {
            String[] split = path.split("/");
            //不存在
            return !STATIC_RESOURCE.contains(split[1]);
        }
        return false;
    }

}
