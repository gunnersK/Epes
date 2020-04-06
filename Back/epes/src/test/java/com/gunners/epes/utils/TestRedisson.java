package com.gunners.epes.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testExist(){
        RMap map = redissonClient.getMap("mapaaa");
        map.put("key1", "map1");
    }

    @Test
    public void testExist2(){
        RMap map = redissonClient.getMap("mapaaa");
        map.forEach((k, v) -> {
            System.out.println(k+":"+v);
        });
    }

    @Test
    public void testQueue(){
        RQueue queue = redissonClient.getQueue("q1");
        ArrayList a = new ArrayList();
        a.add(1);
        System.out.println(a.size());
//        for(int i = 0; i < 10; i++){
//            queue.add(""+i);
//        }
//        queue.remove("2");
//        while(!queue.isEmpty()){
//            System.out.println(queue.poll());
//        }
    }

    @Test
    public void testSet(){
        Set set = Sets.newHashSet("a");
        System.out.println(set.contains("a"));
    }

    @Test
    public void testList(){
        List list = Lists.newArrayList();
        list.forEach( v -> {
            System.out.println(v);
        });
    }

    @Test
    public void testExpire(){
        RMap map = redissonClient.getMap("expire");
        map.put("1", "b");
        map.expire(5, TimeUnit.SECONDS);
        while(true){
            try{
                TimeUnit.SECONDS.sleep(1);
                System.out.println(map.remainTimeToLive());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}

