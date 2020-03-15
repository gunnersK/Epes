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

    public boolean deleteUserByEmpId(Employee employee);

    public int updateUserByEmpId(User user);

    public User authentication(User user);

    public boolean validPasswd(HttpSession session, String old_passwd);

}
