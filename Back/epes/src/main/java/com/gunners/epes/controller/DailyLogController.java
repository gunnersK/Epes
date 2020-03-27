package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.DailyLog;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.DailyLogVo;
import com.gunners.epes.entity.vo.TaskEvaVo;
import com.gunners.epes.service.IDailyLogService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Objects;

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
        long currentTime = Instant.now().getEpochSecond();

        //在redis获取员工信息
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        dailyLog.setEmpId(empInfo.getEmpId())
                .setEmpName(empInfo.getEmpName())
                .setStatus(0)
                .setCreateTime(currentTime)
                .setLastUpdTime(currentTime);

        //入库
        dailyLogService.save(dailyLog);
        saveCacheService.saveDailyLog(dailyLog);
        return Response.ok(dailyLog);
    }

    //页面间传递过滤条件
    @PostMapping("/transFilter")
    public Response transmitFilter(DailyLogVo dailyLogVo, HttpSession session){
        clearFilter(session);
        if (!Objects.isNull(dailyLogVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_START_TIME, dailyLogVo.getStartTime());
        }
        if (!Objects.isNull(dailyLogVo.getEndTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_END_TIME, dailyLogVo.getEndTime());
        }
        if (!Objects.isNull(dailyLogVo.getEmpId())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_EMP_ID, dailyLogVo.getEmpId());
        }
        if (!Objects.isNull(dailyLogVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_START_TIME, dailyLogVo.getStartTime());
        }
        return Response.ok();
    }

    //接下来做list接口，然后做员工查看所有日志调用的listByEmpId接口，直接调用list接口就得
    //主管日志列表调用
//    @GetMapping("/list")
//    public Response listDailyLog();

    //员工查看所有日志调用
//    @GetMapping("/listByEmpId")
//    public Response listByEmpId();
//    要获取当前员工id
//    调用listDailyLog，做法参照TaskEvaController

    private void clearFilter(HttpSession session){
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_END_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_EMP_ID);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_STATUS);
    }

}
