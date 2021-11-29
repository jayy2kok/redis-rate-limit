package com.jayraj.redis.ratelimit.redisratelimit.configuration;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedissonReactiveClient getReactiveRedissonClient(@Autowired RedisServer redisServer) {
        redisServer.start();
        return Redisson.create().reactive();
    }

    @Bean
    public RedisServer getRedisServer() throws IOException {
        return new RedisServer(6379);
    }
}
