package com.gunners.epes.controller;


import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Login;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.IEmpInfoService;
import com.gunners.epes.service.ILoginService;
import com.gunners.epes.service.IUserService;
import com.gunners.epes.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Objects;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    IUserService userService;

    @Autowired
    ILoginService loginService;

    @Autowired
    IEmpInfoService empInfoService;

    @PostMapping("/login")
    public Response login(User user, HttpSession session){

        //用户认证
        User u = userService.authentication(user);

        if(!Objects.isNull(u)){
            //初始化session
            sessionUtils.initSession(session);

            //更新user最后登录时间
            long currentTime = Instant.now().getEpochSecond();
            u.setLastLoginTime(currentTime);
            userService.updateById(u);

            //在session保存User和EmpInfo信息
            sessionUtils.putIntoSession(session, SessionKeyConstants.USER, u);

            EmpInfo empInfo = empInfoService.getEmpInfo(user.getEmpId());
            sessionUtils.putIntoSession(session, SessionKeyConstants.EMP_INFO, empInfo);

            //记录到login表
            Login login = new Login()
                    .setUserId(u.getUserId())
                    .setLoginTime(currentTime);
            loginService.save(login);

            log.info(StrUtil.format("employee_{} login success! user_id_{}", u.getEmpId(), u.getUserId()));
            return Response.ok();
        }
        log.error(StrUtil.format("employee_{} login failure!", user.getEmpId()));
        return Response.ok(null, "failure");
    }

    @GetMapping("/getUser")
    public Response getUser(HttpSession session){
        User user = sessionUtils.getFromSession(session, SessionKeyConstants.USER);
        return Response.ok(user);
    }

    @PostMapping("/logout")
    public Response logout(HttpSession session){
        sessionUtils.clearSession(session);
        return Response.ok();
    }

}
