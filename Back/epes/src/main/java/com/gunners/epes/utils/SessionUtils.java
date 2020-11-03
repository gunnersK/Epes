package com.gunners.epes.utils;

import com.gunners.epes.constants.SessionKeyConstants;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Component
public class SessionUtils {

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 初始化session，设置有效时间
     * 为每个user在redis中存一个RMap当做session
     * 保存用户自己的相关信息，比如页面间传递的id等等
     * @param session
     */
    public void initSession(HttpSession session){
        RMap map = getSession(session);
        map.put(SessionKeyConstants.SESSIONID, session.getId());
        map.expire(30, TimeUnit.MINUTES);  //有效时间为30分钟
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

    /**
     * 从redis的session map中清除信息
     * @param session
     * @param key
     */
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
