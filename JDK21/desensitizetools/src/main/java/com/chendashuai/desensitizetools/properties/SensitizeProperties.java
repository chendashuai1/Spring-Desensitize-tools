package com.chendashuai.desensitizetools.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "desensitize")
public class SensitizeProperties {
    private boolean enabled = true;
    private final PatternProperties patterns = new PatternProperties();

    @Data
    public static class PatternProperties {
        private Map<String, String> rules = new LinkedHashMap<>();
    }
}
