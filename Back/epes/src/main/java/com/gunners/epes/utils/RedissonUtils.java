package com.gunners.epes.utils;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class RedissonUtils {

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 页面间传递id，放进redis
     * @param partnerId
     * @param key
     * @param transmitId
     */
    public void setTransmitId(String partnerId, String key, String transmitId){
        RMap<String, String> map = redissonClient.getMap(partnerId);
        map.put(key, transmitId);
    }

    /**
     * 获取页面间传递的id
     * @param partnerId
     * @param key
     * @return
     */
    public String getTransmitId(String partnerId, String key){
        RMap<String, String> map = redissonClient.getMap(partnerId);
        return map.get(key);
    }

    /**
     * 将用户session保存进redis
     * @param session
     */
    public void saveSession(String sessionID, HttpSession session){
        RMap<String, HttpSession> map = redissonClient.getMap("epes_session");
        map.put(sessionID, session);
    }
}
