package com.jayraj.redis.ratelimit.redisratelimit.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedissonReactiveClient getReactiveRedissonClient() {
        return Redisson.create().reactive();
    }
}
