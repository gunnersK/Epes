package com.gunners.epes.service;

import com.gunners.epes.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IEmployeeService extends IService<Employee> {

    public boolean deleteEmployeeByEmpId(Employee employee);

}
