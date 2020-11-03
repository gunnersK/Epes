package com.gunners.epes.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.IUserService;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    IUserService userService;

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
