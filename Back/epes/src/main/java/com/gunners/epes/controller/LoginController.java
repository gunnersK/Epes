package com.gunners.epes.controller;


import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.ILoginService;
import com.gunners.epes.service.IUserService;
import com.gunners.epes.utils.RedissonUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    RedissonUtils redissonUtils;

    @Autowired
    IUserService userservice;

    @Autowired
    ILoginService loginService;

    @PostMapping
    public Response login(User user, HttpServletRequest req){
        User u = userservice.authentication(user);
        if(!Objects.isNull(u)){
            HttpSession session = req.getSession();
            redissonUtils.saveSession(session.getId(), session);
            return Response.ok();
        }
        return Response.ok(null, "failure");
    }

}
