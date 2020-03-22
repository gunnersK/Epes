package com.gunners.epes.mapper;

import com.gunners.epes.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    Employee getLastEmp(String subId);

    int deleteByDpartId(Integer dpartId);

    List<Employee> listTaskEmployee(Integer dpartId, Integer taskId);

}
