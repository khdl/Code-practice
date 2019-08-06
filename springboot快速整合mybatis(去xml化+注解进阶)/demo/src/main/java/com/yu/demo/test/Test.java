package com.yu.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @className: Test
 * @author: yu.liu
 * @date: 2019/7/5 14:02
 * @description:
 */
@RestController
public class Test {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @GetMapping("test")
    public void test() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("lock");
        boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);
        System.out.println(b1);
        boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
        System.out.println(b2);
        lock.unlock();
        lock.unlock();
    }
}
