package com.gunners.epes.controller;


import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.gunners.epes.service.IEmpInfoService;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.IUserService;
import com.gunners.epes.entity.Response;
import com.gunners.epes.utils.IdUtils;
import com.gunners.epes.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
@Slf4j
public class EmployeeController {

    @Autowired
    RedissonUtils redissonUtils;

    @Autowired
    IdUtils idUtils;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IUserService userService;

    @Autowired
    IEmpInfoService empInfoService;

    @GetMapping("/newId")
    public Response generateEmpId(){
        String newEmpId = idUtils.generateEmpId();
        return Response.ok(newEmpId);
    }

    @PostMapping("/save")
    public Response saveEmployee(Employee employee, User user){
        employeeService.save(employee);
        user.setPassword(Base64.encode(user.getPassword(), CharsetUtil.UTF_8));
        userService.save(user);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteEmployee(Employee employee){
        employeeService.deleteByEmpId(employee);
        userService.deleteUserByEmpId(employee);
        return Response.ok();
    }

    @PostMapping("/update")
    public Response updateEmployee(Employee employee, User user){
        employeeService.updateEmployee(employee);
        userService.updateUserByEmpId(user);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listEmployee(){
        Integer dpartId = Integer.valueOf(redissonUtils.getTransmitId("admin_id", "dpart_id"));
        List list = employeeService.listByDpartId(dpartId);
        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(String empId){
        redissonUtils.setTransmitId("admin_id", "emp_id", empId);
        return Response.ok();
    }

    @GetMapping("/getEmpInfo")
    public Response getEmpInfo(){
        String empId = redissonUtils.getTransmitId("admin_id", "emp_id");
        EmpInfo empInfo = empInfoService.getEmpInfo(empId);
        return Response.ok(empInfo);
    }

}
