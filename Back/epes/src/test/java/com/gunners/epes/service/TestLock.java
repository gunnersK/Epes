package com.gunners.epes.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestLock {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void aa(){
        RLock lock = redissonClient.getLock("test_lock");

        new Thread( () -> {
            try{
                System.out.println("t1 start");
                lock.tryLock(3, 10, TimeUnit.SECONDS);
                System.out.println("t1 wait");
                Thread.sleep(6000);
                System.out.println("t1 end");
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (lock.isLocked()){
                    lock.unlock();
                }
            }
        }).start();

        try{
            System.out.println("t2 start");
            lock.tryLock(3, 10, TimeUnit.SECONDS);
            System.out.println("t2 wait");
            Thread.sleep(6000);
            System.out.println("t2 end");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (lock.isLocked()){
                lock.unlock();
            }
        }
    }
}
