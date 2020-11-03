package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.gunners.epes.mapper.EmployeeMapper;
import com.gunners.epes.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gunners.epes.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    IUserService userService;

    @Override
    public Employee getOne(String empId) {
        Wrapper queryWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getEmpId, empId);
        return this.getOne(queryWrapper);
}

    @Override
    public boolean deleteByEmpId(Employee employee) {
        Wrapper queryWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getEmpId, employee.getEmpId());
        return this.remove(queryWrapper);
    }

    @Override
    public List<Employee> listByDpartId(Integer dpartId) {
        Wrapper queryWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getDpartId, dpartId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public Employee getLastEmp(String subId) {
        return this.baseMapper.getLastEmp(subId);
    }

    @Override
    public int updateEmployee(Employee employee) {
        Wrapper updateWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getEmpId, employee.getEmpId());
        return this.baseMapper.update(employee, updateWrapper);
    }

    @Override
    public boolean deleteByDpartId(Integer dpartId) {
        /*删除user表的记录*/
        Wrapper queryWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getDpartId, dpartId);
        List<Employee> empList = this.list(queryWrapper);
        empList.forEach( emp -> {
            Wrapper userWrapper = new QueryWrapper<User>().lambda()
                    .eq(User::getEmpId, emp.getEmpId());
            userService.remove(userWrapper);
        });

        /*再删除employee表的记录*/
        Wrapper empWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getDpartId, dpartId);
        return remove(empWrapper);
    }

    /**
     * 查询选定任务下未被分配的本部门员工
     * @param dpartId
     * @param taskId
     * @return
     */
    @Override
    public List<Employee> listTaskEmployee(Integer dpartId, Integer taskId) {
        List<Employee> list = this.getBaseMapper().listTaskEmployee(dpartId, taskId);
        return list;
    }
}
