package com.chendashuai.desensitizetools.tools;

import com.chendashuai.desensitizetools.properties.SensitizeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Service
@EnableConfigurationProperties(SensitizeProperties.class)
public class DesensitizeStrategyFactory {

    public static Map<String, DesensitizeStrategy> strategyMap;
    public static String[] keys;
    public static boolean enabled;

    @Autowired
    public DesensitizeStrategyFactory(Map<String, DesensitizeStrategy> map, SensitizeProperties properties) {
        enabled = properties.isEnabled();
        strategyMap = map;
        addStrategy(properties.getPatterns());
        keys = strategyMap.keySet().toArray(new String[0]);
    }

    private void addStrategy(SensitizeProperties.PatternProperties patternProperties) {
        Map<String, String> map = patternProperties.getRules();
        if(map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            Pattern pattern = Pattern.compile(entry.getValue());
            strategyMap.put(key, input -> pattern.matcher(input).replaceAll("***"));
        }
    }

    public static DesensitizeStrategy getStrategy(String name){
        return strategyMap.get(name);
    }

    public static int getCounts() {
        return keys.length;
    }
}