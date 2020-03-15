package com.gunners.epes.utils;

import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.User;
import lombok.Data;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionUtils {

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 为每个user在redis中存一个RMap保存用户信息
     * @param session
     */
    public void saveSession(HttpSession session){
        redissonClient.getMap(session.getId());
    }

    /**
     * 清除session
     */
    public void clearSession(HttpSession session){
        getSession(session).delete();
    }

    /**
     * 往redis的session map中保存信息
     * @param key
     * @param value
     */
    public <T> void putIntoSession(HttpSession session, String key, T value){
        RMap map = getSession(session);
        map.put(key, value);
    }

    /**
     * 从redis的session map中获得信息
     * @param key
     * @param key
     * @return
     */
    public <T> T getFromSession(HttpSession session, String key){
        RMap map = getSession(session);
        return (T) map.get(key);
    }

    public void removeFromSession(HttpSession session, String key){
        RMap map = getSession(session);
        map.remove(key);
    }

    /**
     * 获取redis中的session
     * @param session
     * @return
     */
    private RMap getSession(HttpSession session){
        return redissonClient.getMap(session.getId());
    }
}
