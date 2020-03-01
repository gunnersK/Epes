package com.gunners.epes.service;

import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
