package com.bid.ext.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class TestRedisLock {
//
//    @Autowired
//    private StringRedisTemplate template;
//
//    public String testLock() {
//        String lockKey = "myLockKey";
//        boolean locked = tryLock(lockKey, 10); // 尝试获取锁，超时时间为10秒
//        if (!locked) {
//            return "Lock is occupied.";
//        }
//        try {
//            // 执行需要同步的操作
//            return "Locked and processing...";
//        } finally {
//            unlock(lockKey); // 释放锁
//        }
//    }
//
//    /**
//     * 尝试获取分布式锁
//     * @param lockKey 锁的键
//     * @param timeout 锁的超时时间(秒)
//     * @return 是否获取成功
//     */
//    public boolean tryLock(String lockKey, long timeout) {
//        ValueOperations<String, String> ops = template.opsForValue();
//        Boolean result = ops.setIfAbsent(lockKey, "locked", timeout, TimeUnit.SECONDS);
//        return Boolean.TRUE.equals(result);
//    }
//
//    /**
//     * 释放分布式锁
//     * @param lockKey 锁的键
//     */
//    public void unlock(String lockKey) {
//        template.delete(lockKey);
//    }
}
