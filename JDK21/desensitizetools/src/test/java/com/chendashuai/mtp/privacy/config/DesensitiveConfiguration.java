package com.chendashuai.mtp.privacy.config;


import com.chendashuai.desensitizetools.tools.DesensitizeStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DesensitiveConfiguration {
    @Bean("password")
    public DesensitizeStrategy passwordStrategy() {
        return password -> "*****";
    }

    @Bean("phone")
    public DesensitizeStrategy phoneStrategy() {
        return phone -> {
            if (phone == null || phone.length() < 11) return phone;
            return phone.substring(0, 3) + "****" + phone.substring(7);
        };
    }
}
