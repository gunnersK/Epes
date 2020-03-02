package com.gunners.epes.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisson {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testRedisson(){
        List<String> list = redissonClient.getList("list1");
        list.add("aa");
        list.add("bb");
        list = redissonClient.getList("list1");
        list.forEach( x -> {
            System.out.println(x);
        });
//        System.out.println(redissonClient.getList("list1"));
    }
}

