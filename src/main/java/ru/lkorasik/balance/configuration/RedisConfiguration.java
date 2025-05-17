package ru.lkorasik.balance.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class RedisConfiguration {
    public static final String USER_INFO = "userInfo";
    public static final String USER_SEARCH = "userSearch";
}
