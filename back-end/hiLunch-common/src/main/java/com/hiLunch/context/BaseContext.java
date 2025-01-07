package com.hiLunch.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> thread = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

    public static void setToken(String token) {
        thread.set(token);
    }

    public static String getToken() {
        return thread.get();
    }


}
