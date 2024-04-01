package com.example.service.integrationapp.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.redis",name = "enable",havingValue = "true")
public class RedisConfiguration {
}
