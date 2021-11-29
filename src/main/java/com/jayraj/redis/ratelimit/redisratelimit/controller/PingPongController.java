package com.jayraj.redis.ratelimit.redisratelimit.controller;

import org.redisson.api.RRateLimiterReactive;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import redis.embedded.RedisServer;

@RestController
public class PingPongController {
    @Autowired
    private RedissonReactiveClient redissonClient;

    @Autowired
    RedisServer redisServer;

    @GetMapping("/ping")
    public Mono<String> callPing(@RequestParam Integer clientId) {
        RRateLimiterReactive limiter = redissonClient.getRateLimiter(clientId.toString());
        return limiter.tryAcquire().map(isAllowed -> {
            if (isAllowed) {
                return "PONG";
            } else {
                return "Limit exceeded";
            }
        });

    }

    @GetMapping("/init")
    public Mono<Boolean> initRateLimit(@RequestParam Integer clientId) {

        RRateLimiterReactive limiter = redissonClient.getRateLimiter(clientId.toString());
        return limiter.trySetRate(RateType.OVERALL, 3, 15, RateIntervalUnit.SECONDS);
    }

    @GetMapping("/stop")
    public boolean stopRedisServer() {
        redisServer.stop();
        return true;
    }
}
