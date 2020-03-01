package com.gunners.epes.controller;


import com.gunners.epes.entity.Department;
import com.gunners.epes.service.IDepartmentService;
import com.gunners.epes.entity.Response;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.utils.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    RedissonUtils redissonUtils;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IEmployeeService employeeService;

    @PostMapping("/save")
    public Response saveDepartment(Department department){
        departmentService.save(department);
        return Response.ok();
    }

    @PostMapping("delete")
    public Response deleteDepartmemnt(Department department){
        departmentService.removeById(department.getDpartId());
        employeeService.deleteByDpartId(department.getDpartId());
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listDepartment(){
        List list = departmentService.list();
        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(Integer dpartId){
        redissonUtils.setTransmitId("admin_id", "dpart_id", String.valueOf(dpartId));
        return Response.ok();
    }

    @GetMapping("/getDpart")
    public Response getDpartById(){
        Integer dpartId = Integer.valueOf(redissonUtils.getTransmitId("admin_id", "dpart_id"));
        Department dpart = departmentService.getById(dpartId);
        return Response.ok(dpart);
    }

}
