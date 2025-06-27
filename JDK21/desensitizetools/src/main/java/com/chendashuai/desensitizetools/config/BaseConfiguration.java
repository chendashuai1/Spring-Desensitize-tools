package com.chendashuai.desensitizetools.config;

import com.chendashuai.desensitizetools.properties.SensitizeProperties;
import com.chendashuai.desensitizetools.serializer.SensitiveBeanSerializerModifier;
import com.chendashuai.desensitizetools.tools.DesensitizeStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SensitizeProperties.class)
public class BaseConfiguration {

    private final SensitiveBeanSerializerModifier sensitiveBeanSerializerModifier;

    @Autowired
    public BaseConfiguration(SensitiveBeanSerializerModifier modifier) {
        this.sensitiveBeanSerializerModifier = modifier;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setSerializerModifier(sensitiveBeanSerializerModifier);
        objectMapper.registerModule(simpleModule);// 注册模块
        return objectMapper;
    }

    @Bean("null") //占位脱敏策略，必须要有一个策略。
    public DesensitizeStrategy nullStrategy() {
        return s -> s;
    }
}
