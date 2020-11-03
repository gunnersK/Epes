package com.gunners.epes.service;

import com.gunners.epes.entity.Department;
import com.gunners.epes.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.PrjTask;

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

    Employee getOne(String empId);

    boolean deleteByEmpId(Employee employee);

    List<Employee> listByDpartId(Integer dpartId);

    Employee getLastEmp(String subId);

    int updateEmployee(Employee employee);

    boolean deleteByDpartId(Integer dpartId);

    List<Employee> listTaskEmployee(Integer dpartId, Integer taskId);

}
