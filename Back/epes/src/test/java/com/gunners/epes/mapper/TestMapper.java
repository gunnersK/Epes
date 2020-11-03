package com.gunners.epes.mapper;

import com.gunners.epes.entity.Employee;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.impl.EmployeeServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {

    @Autowired
    IEmployeeService employeeService;

    @Test
    public void testGetLastEmp(){
        Employee e = employeeService.getLastEmp("1012001");
        System.out.println(e.toString());
    }
}
