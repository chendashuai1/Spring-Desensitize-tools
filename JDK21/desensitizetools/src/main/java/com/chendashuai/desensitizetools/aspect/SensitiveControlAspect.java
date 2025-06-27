package com.chendashuai.desensitizetools.aspect;

import com.chendashuai.desensitizetools.annotation.EnableSensitive;
import com.chendashuai.desensitizetools.tools.SensitiveContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SensitiveControlAspect {
    @Around("@within(enableSensitive)")
    //拦截SensitiveControl标注的类
    public Object aroundController(ProceedingJoinPoint joinPoint, EnableSensitive enableSensitive) throws Throwable {
        SensitiveContext.setContext(enableSensitive.enable());
        return joinPoint.proceed();
    }
}
