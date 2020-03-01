package com.gunners.epes.service;

import com.gunners.epes.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IEmployeeService extends IService<Employee> {

    public boolean deleteByEmpId(Employee employee);

    public List<Employee> listByDpartId(Integer dpartId);

    public Employee getLastEmp(String subId);

    public int updateEmployee(Employee employee);

    public boolean deleteByDpartId(Integer dpartId);

}
