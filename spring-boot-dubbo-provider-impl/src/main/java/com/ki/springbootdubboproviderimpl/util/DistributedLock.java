package com.ki.springbootdubboproviderimpl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuebo
 * @description 分布式锁
 * @company ki
 * @create 2019/1/10 17:48
 */
public class DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private StringRedisTemplate stringRedisTemplate;

    public DistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * @Author fanxuebo
     * @Date 2019/1/11 10:10
     * @Description 获取锁，锁的名称，获取锁超时时间，释放锁的超时时间
     **/
    public String lockWithTimeout(String lockName, long timeout) {
        String retIdentifier = null;
        try {
            String identifier = UUID.randomUUID().toString();// 随机生成一个value
            String lockKey = "lock:" + lockName;// 锁名，即key值
            int lockExpire = (int) (timeout / 1000);// 超时时间，上锁后超过此时间则自动释放锁
            while (true) {
                if (stringRedisTemplate.opsForValue().setIfAbsent(lockKey, identifier)) {
                    LOGGER.info("{}:获取到锁lockKey：{}", Thread.currentThread().getName(), lockKey);
                    stringRedisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                    retIdentifier = identifier;// 返回value值，用于释放锁时间确认
                    break;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (stringRedisTemplate.getExpire(lockKey) == -1L) {
                    stringRedisTemplate.expire(lockKey, lockExpire, TimeUnit.SECONDS);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取锁异常:[{}]", e.getStackTrace()[0]);
        }
        return retIdentifier;
    }

    /**
     * @Author fanxuebo
     * @Date 2019/1/11 10:09
     * @Description 释放锁
     **/
    public boolean releaseLock(String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            while (true) {
                stringRedisTemplate.watch(lockKey);// 监视lock，准备开始事务
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                    LOGGER.info("{}:删除锁lockKey：{}", Thread.currentThread().getName(), lockKey);
                    stringRedisTemplate.delete(lockKey);
                    retFlag = true;
                }
                stringRedisTemplate.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("释放锁异常:[{}]", e.getStackTrace()[0]);
        }
        return retFlag;
    }
}