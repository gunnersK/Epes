package com.gunners.epes.controller;


import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.IUserService;
import com.gunners.epes.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IUserService userService;

    @PostMapping("/add")
    public Response addEmployee(Employee employee, User user){
        employeeService.save(employee);
        userService.save(user);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteEmployee(Employee employee){
        employeeService.deleteEmployeeByEmpId(employee);
        userService.deleteUserByEmpId(employee);
        return Response.ok();
    }

}
