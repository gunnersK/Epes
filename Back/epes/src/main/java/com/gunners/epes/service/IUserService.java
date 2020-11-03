package com.gunners.epes.service;

import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IUserService extends IService<User> {

    boolean deleteUserByEmpId(Employee employee);

    int updateUserByEmpId(User user);

    User authentication(User user);

    boolean validPasswd(HttpSession session, String old_passwd);

}
