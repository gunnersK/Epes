package com.gunners.epes.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.*;
//import com.gunners.epes.utils.RedissonUtils;
import com.gunners.epes.utils.SessionUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
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
    RedissonClient redissonClient;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    IUserService userService;

    @Autowired
    ILoginService loginService;

    @Autowired
    IEmployeeService employeeService;

    @PostMapping("/login")
    public Response login(User user, HttpSession session){
        User u = userService.authentication(user);
        if(!Objects.isNull(u)){
            sessionUtils.saveSession(session);
            sessionUtils.putIntoSession(session, SessionKeyConstants.USER, u);
            Employee employee = employeeService.getOne(user.getEmpId());
            sessionUtils.putIntoSession(session, SessionKeyConstants.EMPLOYEE, employee);
            return Response.ok();
        }
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

    @PostMapping("/modifyPwd")
    public Response modifyPassword(HttpSession session, String old_passwd, String new_passwd){
        if(userService.validPasswd(session, old_passwd)){
            User user = sessionUtils.getFromSession(session, SessionKeyConstants.USER);
            user.setPassword(Base64.encode(new_passwd, CharsetUtil.UTF_8));
            userService.updateUserByEmpId(user);
            sessionUtils.putIntoSession(session, SessionKeyConstants.USER, user);
            return Response.ok();
        }
        return Response.ok(null, "failure");
    }

}
