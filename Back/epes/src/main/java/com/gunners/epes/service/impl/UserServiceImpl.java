package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.gunners.epes.mapper.UserMapper;
import com.gunners.epes.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean deleteUserByEmpId(Employee employee) {
        Wrapper queryWrapper = new QueryWrapper<User>().lambda()
                .eq(User::getEmpId, employee.getEmpId());
        return this.remove(queryWrapper);
    }

    @Override
    public int updateUserByEmpId(User user) {
        Wrapper updateWrapper = new QueryWrapper<User>().lambda()
                .eq(User::getEmpId, user.getEmpId());
        return this.baseMapper.update(user, updateWrapper);
    }
}
