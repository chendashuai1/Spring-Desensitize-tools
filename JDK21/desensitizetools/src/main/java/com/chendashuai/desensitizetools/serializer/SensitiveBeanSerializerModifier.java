package com.chendashuai.desensitizetools.serializer;

import com.chendashuai.desensitizetools.annotation.Sensitive;
import com.chendashuai.desensitizetools.tools.DesensitizeStrategy;
import com.chendashuai.desensitizetools.tools.DesensitizeStrategyFactory;
import com.chendashuai.desensitizetools.tools.SensitiveContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class SensitiveBeanSerializerModifier extends BeanSerializerModifier {
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        if (!DesensitizeStrategyFactory.enabled || Objects.equals(false, SensitiveContext.getContext()))
            return beanProperties;
        for (BeanPropertyWriter beanPropertyWriter : beanProperties) {
            Sensitive annotation = beanPropertyWriter.getAnnotation(Sensitive.class);
            if(annotation == null) continue;
            DesensitizeStrategy strategy = DesensitizeStrategyFactory.getStrategy(annotation.strategy());
            if(strategy == null) continue;
            beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
                    gen.writeString(strategy.desensitize((String) value));
                }
            });
        }
        return beanProperties;
    }
}
