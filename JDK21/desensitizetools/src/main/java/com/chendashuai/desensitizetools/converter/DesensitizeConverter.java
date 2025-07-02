package com.chendashuai.desensitizetools.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.chendashuai.desensitizetools.tools.DesensitizeStrategy;
import com.chendashuai.desensitizetools.tools.DesensitizeStrategyFactory;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DesensitizeConverter extends ClassicConverter {

    private Pattern pattern;
    int count;

    @Override
    public String convert(ILoggingEvent event) {
        return processMessage(event.getFormattedMessage());
    }

    private boolean buildPattern() {
        if (DesensitizeStrategyFactory.keys == null)
            return false;
        String regex = Arrays.stream(DesensitizeStrategyFactory.keys)
                .map(Pattern::quote)
                .map(key -> "(" + key + ")([=:：]\\s*[\"']?)([^,&\\s\"']+)")
                .collect(Collectors.joining("|"));
        count = DesensitizeStrategyFactory.getCounts();
        this.pattern = Pattern.compile(regex);
        return true;
    }

    private String processMessage(String message) {
        if (message == null || !DesensitizeStrategyFactory.enabled || (this.pattern == null && !buildPattern())) {
            return message;
        }
        Matcher matcher = pattern.matcher(message);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            int i = 1;
            while (i < count * 3 && matcher.group(i) == null) {
                i += 3;
            }
            if (i > count * 3) {
                continue;
            }
            String fullKey = matcher.group(i);
            String separator = matcher.group(i + 1);
            String originalValue = matcher.group(i + 2);
            DesensitizeStrategy strategy = DesensitizeStrategyFactory.getStrategy(fullKey);
            if (strategy == null) {
                continue;
            }
            String replacement = strategy.desensitize(originalValue);
            matcher.appendReplacement(result, fullKey + separator + replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
