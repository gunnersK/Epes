package com.gunners.epes.controller;


import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.entity.vo.PrjTaskVo;
import com.gunners.epes.entity.vo.TaskEvaVo;
import com.gunners.epes.service.*;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
@RequestMapping("/taskEva")
public class TaskEvaController {

    @Autowired
    ITaskEvaService taskEvaService;

    @Autowired
    ITaskEvaInfoService taskEvaInfoService;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IGetCacheService getCacheService;


    /**
     * 获取首页统计图数据
     * @param year
     * @param dpartId
     * @param empId
     * @return
     */
    @GetMapping("/getChartData")
    public Response getChartData(Integer year, Integer dpartId, String empId){
        List<Integer> dataList = taskEvaService.queryChartData(year, dpartId, empId);
        return Response.ok(dataList);
    }

    @PostMapping("/save")
    public Response save(HttpSession session, String[] empIdList){
        Integer taskId = sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_ID);
        taskEvaService.saveBatch(taskId, empIdList);
        return Response.ok();
    }

    /**
     * 页面间传递过滤条件
     * @param taskEvaVo
     * @param session
     * @return
     */
    @PostMapping("/transFilter")
    public Response transmitFilter(TaskEvaVo taskEvaVo, HttpSession session){
        clearFilter(session);
        if (!Objects.isNull(taskEvaVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_START_TIME, taskEvaVo.getStartTime());
        }
        if (!Objects.isNull(taskEvaVo.getEndTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_END_TIME, taskEvaVo.getEndTime());
        }
        if (StrUtil.isNotBlank(taskEvaVo.getEmpId())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_EMP_ID, taskEvaVo.getEmpId());
        }
        if (!Objects.isNull(taskEvaVo.getPrjId())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_PRJ_ID, taskEvaVo.getPrjId());
        }
        if (!Objects.isNull(taskEvaVo.getStatus())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_STATUS, taskEvaVo.getStatus());
        }
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listTaskEva(HttpSession session, TaskEvaVo taskEvaVo){
        //获取过滤条件并查询
        taskEvaVo.setStartTime(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_START_TIME));
        taskEvaVo.setEndTime(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_END_TIME));
        taskEvaVo.setEmpId(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_EMP_ID));
        taskEvaVo.setDpartId(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_DPART_ID));
        taskEvaVo.setPrjId(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_PRJ_ID));
        taskEvaVo.setStatus(sessionUtils.getFromSession(session, SessionKeyConstants.EVA_STATUS));
        List<TaskEvaInfo> list = taskEvaInfoService.listTaskEva(taskEvaVo);

        return Response.ok(list);
    }

    /**
     * 主管绩效评分调用
     * @param session
     * @param taskEvaVo
     * @return
     */
    @GetMapping("/listByDPartId")
    public Response listByDPartId(HttpSession session, TaskEvaVo taskEvaVo){
        //获取当前主管部门id
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_DPART_ID, empInfo.getDpartId());

        Response response = listTaskEva(session, taskEvaVo);
        return response;
    }

    /**
     * 员工绩效列表调用
     * @param session
     * @param taskEvaVo
     */
    @GetMapping("/listByEmpId")
    public Response listByEmpId(HttpSession session, TaskEvaVo taskEvaVo){
        //获取当前员工id
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        sessionUtils.putIntoSession(session, SessionKeyConstants.EVA_EMP_ID, empInfo.getEmpId());

        Response response = listTaskEva(session, taskEvaVo);
        return response;
    }

    @PostMapping("/transId")
    public Response transmitId(HttpSession session, Integer evaId){
        sessionUtils.putIntoSession(session, SessionKeyConstants.TASKEVA_ID, evaId);
        return Response.ok();
    }

    @GetMapping("/getEva")
    public Response getTaskEvaInfo(HttpSession session){
        Integer taskId = sessionUtils.getFromSession(session, SessionKeyConstants.TASKEVA_ID);
        TaskEvaInfo taskEvaInfo = getCacheService.getTaskEvaInfo(taskId);
        return Response.ok(taskEvaInfo);
    }

    @PostMapping("/finish")
    //赋值完成时间，更新状态，绩效,用taskEvaInfo接，更新redis、数据库
    public Response finishTaskEva(HttpSession session, BigDecimal score){
        Integer evaId = sessionUtils.getFromSession(session, SessionKeyConstants.TASKEVA_ID);

        //更新状态、完成时间、绩效，更新进redis
        TaskEvaInfo taskEvaInfo = getCacheService.getTaskEvaInfo(evaId);
        taskEvaInfo.setFinishTime(Instant.now().getEpochSecond())
                   .setStatus(1)
                  .setScore(score);
        Double tmp = Double.valueOf(score.toString()) * taskEvaInfo.getWeight() * 0.1;
        taskEvaInfo.setPerformance(BigDecimal.valueOf(tmp));
        saveCacheService.saveTaskEvaInfo(taskEvaInfo);

        //更新mysql
        taskEvaService.updateTaskEva(taskEvaInfo);

        return Response.ok();
    }

    /**
     * 清除所有过滤条件
     * @param session
     * @return
     */
    @PostMapping("clearAllFilter")
    private void clearFilter(HttpSession session){
        sessionUtils.removeFromSession(session, SessionKeyConstants.EVA_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.EVA_END_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.EVA_EMP_ID);
        sessionUtils.removeFromSession(session, SessionKeyConstants.EVA_PRJ_ID);
        sessionUtils.removeFromSession(session, SessionKeyConstants.EVA_STATUS);
    }

}
