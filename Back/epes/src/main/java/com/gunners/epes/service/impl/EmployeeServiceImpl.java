package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.mapper.EmployeeMapper;
import com.gunners.epes.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean deleteEmployeeByEmpId(Employee employee) {
        Wrapper queryWrapper = new QueryWrapper<Employee>().lambda()
                .eq(Employee::getEmpId, employee.getEmpId());
        return this.remove(queryWrapper);
    }
}
