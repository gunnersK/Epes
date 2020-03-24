package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.DailyLog;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.Response;
import com.gunners.epes.service.IDailyLogService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/dailyLog")
public class DailyLogController {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private IDailyLogService dailyLogService;

    @Autowired
    private ISaveCacheService saveCacheService;

    @PostMapping("/save")
    public Response save(HttpSession session, DailyLog dailyLog){
        //在redis获取员工姓名
        Employee employee = sessionUtils.getFromSession(session, SessionKeyConstants.EMPLOYEE);
        dailyLog.setEmpName(employee.getEmpName());

        //入库
        dailyLogService.save(dailyLog);
        saveCacheService.saveDailyLog(dailyLog);
        return Response.ok(dailyLog);
    }

    //页面间传递过滤条件
//    @PostMapping("/transFilter")

    //主管日志列表调用
//    @GetMapping("/list")
//    public Response listDailyLog();

    //员工查看所有日志调用
//    @GetMapping("/listByEmpId")
//    public Response listByEmpId();
//    要获取当前员工id
//    调用listDailyLog，做法参照TaskEvaController

}
