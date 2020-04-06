package com.gunners.epes.utils;

import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LockUtils {

    @Autowired
    private RedissonClient redissonClient;

    public RLock getLock(String lockName){
        RLock lock = redissonClient.getLock(lockName);
        return lock;
    }
}
