package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.entity.vo.DailyLogVo;
import com.gunners.epes.entity.vo.TaskEvaVo;
import com.gunners.epes.service.IDailyLogService;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
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

    @Autowired
    private IGetCacheService getCacheService;

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

    /**
     * 页面间传递过滤条件
     * @param dailyLogVo
     * @param session
     * @return
     */
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
        if (!Objects.isNull(dailyLogVo.getStatus())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_STATUS, dailyLogVo.getStatus());
        }
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listDailyLog(HttpSession session, DailyLogVo dailyLogVo){
        //获取过滤条件并查询
        dailyLogVo.setStartTime(sessionUtils.getFromSession(session, SessionKeyConstants.LOG_START_TIME));
        dailyLogVo.setEndTime(sessionUtils.getFromSession(session, SessionKeyConstants.LOG_END_TIME));
        dailyLogVo.setEmpId(sessionUtils.getFromSession(session, SessionKeyConstants.LOG_EMP_ID));
        dailyLogVo.setDpartId(sessionUtils.getFromSession(session, SessionKeyConstants.LOG_DPART_ID));
        dailyLogVo.setStatus(sessionUtils.getFromSession(session, SessionKeyConstants.LOG_STATUS));
        List<DailyLog> list = dailyLogService.listDailyLog(dailyLogVo);

        return Response.ok(list);
}

    /**
     * 主管日志列表调用
     * @param session
     * @param dailyLogVo
     * @return
     */
    @GetMapping("/listByDPartId")
    public Response listByDPartId(HttpSession session, DailyLogVo dailyLogVo){
        //获取当前主管部门id
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_DPART_ID, empInfo.getDpartId());

        Response response = listDailyLog(session, dailyLogVo);
        return response;
    }

    /**
     * 员工查看所有日志调用
     * @param session
     * @param dailyLogVo
     * @return
     */
    @GetMapping("/listByEmpId")
    public Response listByEmpId(HttpSession session, DailyLogVo dailyLogVo){
        //获取当前员工id
        User user = sessionUtils.getFromSession(session, SessionKeyConstants.USER);
        sessionUtils.putIntoSession(session, SessionKeyConstants.LOG_EMP_ID, user.getEmpId());

        Response response = listDailyLog(session, dailyLogVo);
        return response;
    }

    @PostMapping("/transId")
    public Response transmitId(HttpSession session, Integer id){
        sessionUtils.putIntoSession(session, SessionKeyConstants.DAILY_LOG_ID, id);
        return Response.ok();
    }

    @GetMapping("/getDailyLog")
    public Response getDailyLog(HttpSession session){
        Integer id = sessionUtils.getFromSession(session, SessionKeyConstants.DAILY_LOG_ID);
        DailyLog dailyLog = getCacheService.getDailyLog(id);
        return Response.ok(dailyLog);
    }

    /**
     * 审阅
     * @param session
     * @return
     */
    @PostMapping("/read")
    public Response readLog(HttpSession session){
        Integer id = sessionUtils.getFromSession(session, SessionKeyConstants.DAILY_LOG_ID);

        //更新状态、完成时间，更新进redis
        DailyLog dailyLog = getCacheService.getDailyLog(id);
        dailyLog.setLastUpdTime(Instant.now().getEpochSecond())
                .setStatus(1);
        saveCacheService.saveDailyLog(dailyLog);

        //更新mysql
        dailyLogService.updateById(dailyLog);

        return Response.ok();
    }

    /**
     * 清除所有过滤条件
     * @param session
     * @return
     */
    @PostMapping("clearAllFilter")
    private void clearFilter(HttpSession session){
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_END_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_EMP_ID);
        sessionUtils.removeFromSession(session, SessionKeyConstants.LOG_STATUS);
    }

}
