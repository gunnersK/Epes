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
     * 保存用户信息
     */
    private RMap session;

    /**
     * 为每个user在redis中存一个RMap保存用户信息
     * @param sessionID
     */
    public void saveSession(String sessionID){
        this.session = redissonClient.getMap(sessionID);
    }

    /**
     * 清除session
     */
    public void clearSession(){
        session.delete();
    }

    /**
     * 往redis的session map中保存信息
     * @param key
     * @param value
     */
    public void putIntoSession(String key, Object value){
        session.put(key, value);
    }

    /**
     * 从redis的session map中获得信息
     * @param key
     * @param key
     * @return
     */
    public Object getFromSession(String key){
        return session.get(key);
    }
}
