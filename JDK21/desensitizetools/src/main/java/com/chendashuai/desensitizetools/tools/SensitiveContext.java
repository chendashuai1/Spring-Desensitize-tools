package com.chendashuai.desensitizetools.tools;

public class SensitiveContext {
    private static final ThreadLocal<Boolean> SEN_FLAG = new ThreadLocal<>();

    public static void setContext(Boolean flag) {
        SEN_FLAG.set(flag);
    }

    public static Boolean getContext() {
        return SEN_FLAG.get();
    }

    public static void clear() {
        SEN_FLAG.remove();
    }
}