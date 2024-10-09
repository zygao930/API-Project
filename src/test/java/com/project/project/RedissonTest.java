package com.project.project;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RedissonTest {
    static final Logger logger = LoggerFactory.getLogger(RedissonTest.class);
    // 注入 RedissonClient
    @Autowired
    RedissonClient redissonClient;
    // 计数器
    private int count;
    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                // 每个线程都创建自己的锁对象
                // 这是基于 Redis 实现的分布式锁
                Lock lock = this.redissonClient.getLock("counterLock");
                try {
                    // 上锁
                    lock.lock();
                    // 计数器自增 1
                    this.count = this.count + 1;
                } finally {
                    // 释放锁
                    lock.unlock();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        logger.info("count = {}", this.count);
    }
}