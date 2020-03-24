package com.gunners.epes.controller;


import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.service.*;
import com.gunners.epes.utils.IdUtils;
import com.gunners.epes.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    SessionUtils sessionUtils;

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

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IClearCacheService clearCacheService;

    @Autowired
    IGetCacheService getCacheService;

    @GetMapping("/newId")
    public Response generateEmpId(HttpSession session){
        String newEmpId = idUtils.generateEmpId(session);
        return Response.ok(newEmpId);
    }

    @PostMapping("/save")
    public Response saveEmployee(Employee employee, User user){
        employeeService.save(employee);
        user.setPassword(Base64.encode(user.getPassword(), CharsetUtil.UTF_8));
        userService.save(user);
        EmpInfo empInfo = empInfoService.getEmpInfo(user.getEmpId());
        saveCacheService.saveEmpInfo(empInfo);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteEmployee(Employee employee){
        employeeService.deleteByEmpId(employee);
        userService.deleteUserByEmpId(employee);
        clearCacheService.deleteEmpInfo(employee.getEmpId());
        return Response.ok();
    }

    @PostMapping("/update")
    public Response updateEmployee(Employee employee, User user){
        employeeService.updateEmployee(employee);
        userService.updateUserByEmpId(user);
        EmpInfo empInfo = empInfoService.getEmpInfo(user.getEmpId());
        saveCacheService.saveEmpInfo(empInfo);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listEmployee(HttpSession session){
        Integer dpartId = sessionUtils.getFromSession(session, SessionKeyConstants.DPART_ID);
        List list = employeeService.listByDpartId(dpartId);
        return Response.ok(list);
    }

    /**
     * 通讯录列表
     * @param dpartId
     * @return
     */
    @GetMapping("/listCtc")
    public Response listEmployee(Integer dpartId){
        List list = employeeService.listByDpartId(dpartId);
        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(HttpSession session, String empId){
        sessionUtils.putIntoSession(session, SessionKeyConstants.EMP_ID, empId);
        return Response.ok();
    }

    /**
     * 管理员部门管理调用
     * @param session
     * @return
     */
    @GetMapping("/getEmpInfo")
    public Response getEmpInfo(HttpSession session){
        String empId = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_ID);
        EmpInfo empInfo = getCacheService.getEmpInfo(empId);
        return Response.ok(empInfo);
    }

    /**
     * 获取个人资料
     * @param session
     * @return
     */
    @GetMapping("/getProfile")
    public Response getProfile(HttpSession session){
        User user = sessionUtils.getFromSession(session, SessionKeyConstants.USER);
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        return Response.ok(empInfo);
    }

    /**
     * 主管任务分配选择员工调用
     * 查询选定任务下未被分配的本部门员工
     * @param session
     * @return
     */
    @GetMapping("/getTaskEmp")
    public Response getTaskEmployee(HttpSession session){
        //获取部门id和任务id
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        Integer dpartId = empInfo.getDpartId();
        Integer taskId = sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_ID);

        //查询员工
        List<Employee> list = employeeService.listTaskEmployee(dpartId, taskId);

        return Response.ok(list);
    }

    /**
     * 主管绩效过滤页面调用
     * @param session
     * @return
     */
    @GetMapping("/listByDpart")
    public Response listByDpartId(HttpSession session){
        //获取部门id
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        Integer dpartId = empInfo.getDpartId();

        //查询员工
        List<Employee> list = employeeService.listByDpartId(dpartId);
        return Response.ok(list);
    }

}
