package com.xuecheng.commons.utils;


import com.xuecheng.commons.model.vo.AuthInfo;

/**
 * 存放用户id的容器
 */
public class AuthInfoHolder {

    private final static ThreadLocal<AuthInfo> threadLocal = new ThreadLocal<>();

    /**
     * 获取线程中的用户
     */
    public static AuthInfo getAuthInfo() {
        return threadLocal.get();
    }

    /**
     * 设置当前线程中的用户
     */
    public static void setAuthInfo(AuthInfo info) {
        threadLocal.set(info);
    }

    public static Long getUserId() {
        return threadLocal.get().getUid();
    }

    public static Long getCompanyId() {
        if(threadLocal.get() != null) {
            return threadLocal.get().getCompanyId();
        }else {
            return null;
        }

    }

    public static void remove(){threadLocal.remove();}

}
