package com.chendashuai.desensitizetools.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 仅作用于类
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableSensitive {
    boolean enable() default true; // 类默认启用脱敏
}
