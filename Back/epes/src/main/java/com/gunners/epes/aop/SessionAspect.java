package com.gunners.epes.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class SessionAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("execution(* javax.servlet.http.HttpServlet.service(..))")
    public void aspect(){}

    /**
     * session续命处理
     * @param joinpoint
     */
    @Before("aspect()")
    public void handleSession(JoinPoint joinpoint){
        Object[] args = joinpoint.getArgs();
        for(Object arg : args){
            if(arg instanceof ServletRequest){
                HttpServletRequest request = (HttpServletRequest) arg;
                String sessionID = request.getSession().getId();
                RMap map = redissonClient.getMap(sessionID);
                if(map.remainTimeToLive() > 0){
                    map.expire(30, TimeUnit.MINUTES);
                }
            }
        }
    }
}
