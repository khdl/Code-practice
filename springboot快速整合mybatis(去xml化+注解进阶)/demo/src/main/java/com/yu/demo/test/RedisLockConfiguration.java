package com.yu.demo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @className: RedisLockConfiguration
 * @author: yu.liu
 * @date: 2019/7/5 13:59
 * @description:
 */
@Configuration
public class RedisLockConfiguration {

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory){
        return  new RedisLockRegistry(redisConnectionFactory, "spring-cloud");
    }
}
